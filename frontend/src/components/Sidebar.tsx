import { Search, Plus } from 'lucide-react';
import { Input } from './ui/input.tsx';
import { Button } from './ui/button.tsx';
import { ScrollArea } from './ui/scroll-area.tsx';
import { Slider } from './ui/slider.tsx';
import type { DragEvent } from 'react';
import { useEffect, useState } from 'react';
import type { Streamer, Position } from "../Home";

interface SidebarProps {
  streamers: Streamer[];
  selectedStreamerId: string;
  searchQuery: string;
  onSearchChange: (query: string) => void;
  minScore: number;
  maxScore: number;
  onMinScoreChange: (value: number) => void;
  onMaxScoreChange: (value: number) => void;
  onViewDetails: (streamer: Streamer) => void;
  onDeleteStreamer: (id: string) => void;
  onAddStreamer: () => void;
  onDragStart: (id: string) => (e: DragEvent<HTMLDivElement>) => void;
  onDragEnd: () => void;
  scoreMax: number;
  selectedPositions: Position[];
  onTogglePosition: (pos: Position) => void;
}

export function Sidebar({
                          streamers,
                          selectedStreamerId,
                          searchQuery,
                          onSearchChange,
                          minScore,
                          maxScore,
                          onMinScoreChange,
                          onMaxScoreChange,
                          onViewDetails,
                          onDeleteStreamer,
                          onAddStreamer,
                          onDragStart,
                          onDragEnd,
                          scoreMax,
                          selectedPositions,
                          onTogglePosition,
                        }: SidebarProps) {
  // ✅ 프롭으로부터 초기값 세팅
  const [scoreRange, setScoreRange] = useState<number[]>([minScore, maxScore]);

  // ✅ 밖에서 min/max가 바뀌면 슬라이더도 맞춰주기
  useEffect(() => {
    setScoreRange([minScore, maxScore]);
  }, [minScore, maxScore]);

  const handleScoreRangeChange = (value: number[]) => {
    setScoreRange(value);
    onMinScoreChange(value[0]);
    onMaxScoreChange(value[1]);
  };

  return (
      <aside className="fixed left-0 top-16 h-[calc(100vh-4rem)] w-80 border-r border-neutral-200 bg-white shadow-sm flex flex-col overflow-hidden">
        <div className="p-4 border-b border-neutral-200 space-y-4">
          <Button
              onClick={onAddStreamer}
              className="w-full"
              variant="outline"
          >
            <Plus className="size-4 mr-2" />
            스트리머 추가
          </Button>

          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 size-4 text-neutral-400" />
            <Input
                type="text"
                placeholder="Search streamer"
                value={searchQuery}
                onChange={(e) => onSearchChange(e.target.value)}
                className="pl-9"
            />
          </div>
          <div className="space-y-2">
            <div className="grid grid-cols-5 gap-1">
              {[
                { label: "Top", value: "Top" as Position },
                { label: "Jungle", value: "Jungle" as Position },
                { label: "Mid", value: "Mid" as Position },
                { label: "AD", value: "Bottom" as Position },
                { label: "Support", value: "Support" as Position },
              ].map((pos) => {
                const active = selectedPositions.includes(pos.value);
                return (
                    <button
                        key={pos.value}
                        onClick={() => onTogglePosition(pos.value)}
                        // 1. Tailwind 클래스로 기본적인 모양과 동작 유지
                        className={`
                          flex items-center justify-center 
                          h-8 px-3 py-1 text-xs rounded-md 
                          font-medium transition-colors whitespace-nowrap
                          disabled:pointer-events-none disabled:opacity-50
                          ${active ? 'hover:bg-neutral-800' : 'hover:bg-neutral-300'}
                          border-0 outline-none // 테두리 초기화
                        `}
                        // 2. 인라인 스타일로 배경색과 글자색을 강제 적용
                        style={{
                          backgroundColor: active ? '#171717' : '#e5e5e5',
                          color: active ? 'white' : '#404040',
                        }}
                    >
                      {pos.label}
                    </button>
                );
              })}
            </div>
          </div>
          {/* ✅ 슬라이더 UI + 프롭 연동 */}
          <div className="space-y-2">
            <div className="flex items-center justify-between">
              <label className="text-neutral-700">Score Range</label>
              <span className="text-neutral-500">
              {scoreRange[0]} - {scoreRange[1]}
            </span>
            </div>
            <Slider
                min={0}
                max={scoreMax}
                step={1}
                value={scoreRange}
                onValueChange={handleScoreRangeChange}
                className="w-full"
            />
          </div>
        </div>

        <div className="flex-1 overflow-y-auto">
          <ScrollArea className="h-full">
            <div className="p-4 space-y-2">
              {streamers.length === 0 ? (
                  <p className="text-neutral-400 text-center py-8">No streamers found</p>
              ) : (
                  streamers.map((streamer) => (
                      <div
                          key={streamer.id}
                          className={`flex items-center justify-between p-3 rounded-lg border transition-colors cursor-move ${
                              selectedStreamerId === streamer.id
                                  ? 'bg-blue-50 border-blue-200'
                                  : 'bg-neutral-50 border-neutral-200 hover:bg-neutral-100'
                          }`}
                          onClick={() => onViewDetails(streamer)}
                          draggable
                          onDragStart={(e) => {
                            e.stopPropagation();
                            console.log("SIDEBAR STREAMER:", streamer);
                            e.dataTransfer.effectAllowed = "move";
                            e.dataTransfer.setData("text/plain", streamer.id);
                            onDragStart(streamer.id)(e);
                          }}
                          onDragEnd={onDragEnd}
                      >
                        <div className="flex-1 min-w-0">
                          <div className="flex items-center gap-2 mb-1">
                            <p className="text-neutral-900">{streamer.name}</p>
                            <span className="px-2 py-0.5 rounded bg-neutral-900 text-white text-xs">
                        {streamer.score}
                      </span>
                          </div>
                          <p className="text-neutral-500">
                            {streamer.position} · {streamer.rank}
                          </p>
                        </div>
                        <div className="flex gap-1 ml-2">
                          <Button
                              variant="ghost"
                              size="sm"
                              onClick={(e) => {
                                e.stopPropagation();
                                onViewDetails(streamer);
                              }}
                          >
                            View
                          </Button>
                          <Button
                              variant="ghost"
                              size="sm"
                              onClick={(e) => {
                                e.stopPropagation();
                                onDeleteStreamer(streamer.id);
                              }}
                              className="text-red-500 hover:text-red-600"
                          >
                            Delete
                          </Button>
                        </div>
                      </div>
                  ))
              )}
            </div>
          </ScrollArea>
        </div>
      </aside>
  );
}
