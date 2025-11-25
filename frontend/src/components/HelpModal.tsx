import { X } from 'lucide-react';
import { Button } from './ui/button.tsx';

interface HelpModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export function HelpModal({ isOpen, onClose }: HelpModalProps) {
  if (!isOpen) return null;

  return (
    <div
      className="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      onClick={onClose}
    >
      <div
        className="bg-white rounded-lg shadow-xl w-full max-w-2xl mx-4"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex items-center justify-between p-6 border-b border-neutral-200">
          <h2 className="text-neutral-900">TeamMaker 사용 설명</h2>
          <Button variant="ghost" size="icon" onClick={onClose}>
            <X className="size-5" />
          </Button>
        </div>

        <div className="p-6 space-y-4">
          <p className="text-neutral-700">
            이 페이지는 스트리머와 테이블을 관리하는 화면입니다.
          </p>
          <ul className="space-y-2 text-neutral-700 list-disc list-inside">
            <li>왼쪽에서 스트리머를 드래그해 테이블 포지션에 배치할 수 있습니다.</li>
            <li>스트리머의 포지션/점수를 기반으로 필터링할 수 있습니다.</li>
            <li>테이블 상단 드롭다운에서 스트리머의 기본 리스트를 선택할 수 있습니다.</li>
            <li>초기화 버튼으로 테이블을 비울 수 있습니다.</li>
            <li>테이블 이름은 클릭하여 수정할 수 있습니다.</li>
            <li>스트리머 추가 버튼으로 새로운 스트리머를 등록할 수 있습니다.</li>
            <li>각 테이블의 Total 행에서 전체 점수를 확인할 수 있습니다.</li>
          </ul>
        </div>

        <div className="p-6 border-t border-neutral-200 flex justify-end">
          <Button onClick={onClose}>확인</Button>
        </div>
      </div>
    </div>
  );
}
