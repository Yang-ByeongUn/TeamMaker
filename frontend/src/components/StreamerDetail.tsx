//import { Card, CardContent, CardHeader, CardTitle } from './ui/card.tsx';
import { Trophy, TrendingUp, Gamepad2, Users } from 'lucide-react';
import type { Streamer, TableData } from "../Home";

interface StreamerDetailProps {
  streamer: Streamer;
  selectedTable: TableData;
}

export function StreamerDetail({ streamer, selectedTable }: StreamerDetailProps) {
  return (
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-neutral-900">스트리머 상세</h2>
        </div>
        <div className="grid grid-cols-7 gap-8">
          <div>
            <p className="text-neutral-500 mb-2">이름</p>
            <p className="text-neutral-900">{streamer.name}</p>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">포지션</p>
            <div className="flex items-center gap-2">
              <Gamepad2 className="size-4 text-neutral-400" />
              <p className="text-neutral-900">{streamer.position}</p>
            </div>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">점수</p>
            <p className="text-neutral-900">{streamer.score}</p>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">랭크</p>
            <div className="flex items-center gap-2">
              <Trophy className="size-4 text-amber-500" />
              <p className="text-neutral-900">{streamer.rank}</p>
            </div>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">승률</p>
            <div className="flex items-center gap-2">
              <TrendingUp className="size-4 text-green-500" />
              <p className="text-neutral-900">{streamer.winRate}</p>
            </div>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">보유 리스트</p>
            <p className="text-neutral-900">{streamer.tableLists.length}개</p>
          </div>

          <div>
            <p className="text-neutral-500 mb-2">선택된 테이블</p>
            <div className="flex items-center gap-2">
              <Users className="size-4 text-blue-500" />
              <p className="text-neutral-900">{selectedTable.name}</p>
            </div>
          </div>
        </div>
      </div>
  );
}