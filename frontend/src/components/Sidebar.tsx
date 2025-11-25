import { Search, Plus } from 'lucide-react';
import { Input } from './ui/input.tsx';
import { Button } from './ui/button.tsx';
import { ScrollArea } from './ui/scroll-area.tsx';
//import { Slider } from './ui/slider.tsx';
import type { Streamer } from "../Home"
import type { DragEvent } from 'react';

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
  scoreMax
}: SidebarProps) {
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
          <div className="flex items-center justify-between">
            <label className="text-neutral-700">Score Range</label>
            <span className="text-neutral-500">
              {minScore} - {maxScore}
            </span>
          </div>
          <div className="relative h-5">
            <input
              type="range"
              min={0}
              max={scoreMax}
              value={minScore}
              onChange={(e) => {
                const value = Number(e.target.value);
                if (value <= maxScore) onMinScoreChange(value);
              }}
              className="absolute w-full h-2 bg-neutral-200 rounded-lg appearance-none cursor-pointer"
              style={{
                background: `linear-gradient(to right, #e5e5e5 0%, #e5e5e5 ${(minScore / scoreMax) * 100}%, #3b82f6 ${(minScore / scoreMax) * 100}%, #3b82f6 ${(maxScore / scoreMax) * 100}%, #e5e5e5 ${(maxScore / scoreMax) * 100}%, #e5e5e5 100%)`,
              }}
            />
            <input
              type="range"
              min={0}
              max={scoreMax}
              value={maxScore}
              onChange={(e) => {
                const value = Number(e.target.value);
                if (value >= minScore) onMaxScoreChange(value);
              }}
              className="absolute w-full h-2 bg-transparent rounded-lg appearance-none cursor-pointer"
            />
          </div>
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
                  onDragStart={onDragStart(streamer.id)}
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