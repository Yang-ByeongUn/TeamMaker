import { useState } from 'react';
import { X } from 'lucide-react';
import { Button } from './ui/button.tsx';
import { Input } from './ui/input.tsx';
import { Label } from './ui/label.tsx';
import type { Position } from '../Home.tsx';

interface AddStreamerModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAdd: (name: string, position: Position, rank: string, score: number) => void;
  scoreMax: number;
}

const positions: Position[] = ['Top', 'Jungle', 'Mid', 'Bottom', 'Support'];

// 백엔드 enum Tier 와 동일하게
const tiers = [
  'IRON',
  'BRONZE',
  'SILVER',
  'GOLD',
  'PLATINUM',
  'EMERALD',
  'DIAMOND',
  'MASTER',
  'GRANDMASTER',
  'CHALLENGER',
] as const;
type Tier = (typeof tiers)[number];

export function AddStreamerModal({ isOpen, onClose, onAdd, scoreMax }: AddStreamerModalProps) {
  const [name, setName] = useState('');
  const [position, setPosition] = useState<Position>('Mid');
  const [rank, setRank] = useState<Tier>('GOLD'); // 기본값 아무거나
  const [score, setScore] = useState<number>(80);

  if (!isOpen) return null;

  const handleSubmit = () => {
    if (!name.trim()) {
      alert('이름을 입력해주세요.');
      return;
    }

    if (score > scoreMax) {
      alert(`점수는 ${scoreMax} 이하만 가능합니다.`);
      return;
    }

    onAdd(name, position, rank, score);

    // Reset form
    setName('');
    setPosition('Mid');
    setRank('GOLD');
    setScore(80);
    onClose();
  };

  const handleCancel = () => {
    setName('');
    setPosition('Mid');
    setRank('GOLD');
    setScore(80);
    onClose();
  };

  return (
      <div
          className="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
          onClick={handleCancel}
      >
        <div
            className="bg-white rounded-lg shadow-xl w-full max-w-md mx-4"
            onClick={(e) => e.stopPropagation()}
        >
          <div className="flex items-center justify-between p-6 border-b border-neutral-200">
            <h2 className="text-neutral-900">스트리머 추가</h2>
            <Button variant="ghost" size="icon" onClick={handleCancel}>
              <X className="size-5" />
            </Button>
          </div>

          <div className="p-6 space-y-4">
            {/* 이름 */}
            <div className="space-y-2">
              <Label htmlFor="name">이름</Label>
              <Input
                  id="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Streamer A"
              />
            </div>

            {/* 포지션 */}
            <div className="space-y-2">
              <Label htmlFor="position">포지션</Label>
              <select
                  id="position"
                  value={position}
                  onChange={(e) => setPosition(e.target.value as Position)}
                  className="flex h-10 w-full rounded-md border border-neutral-200 bg-white px-3 py-2 text-sm"
              >
                {positions.map((pos) => (
                    <option key={pos} value={pos}>
                      {pos}
                    </option>
                ))}
              </select>
            </div>

            {/* 티어 (랭크) 드롭다운 */}
            <div className="space-y-2">
              <Label htmlFor="rank">티어</Label>
              <select
                  id="rank"
                  value={rank}
                  onChange={(e) => setRank(e.target.value as Tier)}
                  className="flex h-10 w-full rounded-md border border-neutral-200 bg-white px-3 py-2 text-sm"
              >
                {tiers.map((tier) => (
                    <option key={tier} value={tier}>
                      {tier}
                    </option>
                ))}
              </select>
            </div>

            {/* 점수 */}
            <div className="space-y-2">
              <Label htmlFor="score">점수</Label>
              <Input
                  id="score"
                  type="number"
                  min={0}
                  max={scoreMax}
                  value={score}
                  onChange={(e) => {
                    let value = Number(e.target.value);

                    if (Number.isNaN(value)) value = 0;
                    if (value > scoreMax) value = scoreMax; // ✅ 100 이상은 100으로 고정
                    if (value < 0) value = 0;

                    setScore(value);
                  }}
              />
            </div>
          </div>

          <div className="p-6 border-t border-neutral-200 flex justify-end gap-2">
            <Button variant="outline" onClick={handleCancel}>
              취소
            </Button>
            <Button onClick={handleSubmit}>추가</Button>
          </div>
        </div>
      </div>
  );
}
