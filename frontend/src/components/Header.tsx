import {HelpCircle, LogOut} from 'lucide-react';
import { Button } from './ui/button.tsx';

interface HeaderProps {
  onHelpClick: () => void;
}

export function Header({ onHelpClick }: HeaderProps) {
  const handleLogout = () => {
    // 로그아웃 로직 (예: 세션 클리어, 리다이렉트 등)
    if (confirm('로그아웃 하시겠습니까?')) {
      // 실제 로그아웃 처리
      console.log('로그아웃 되었습니다.');
      // 예: window.location.href = '/login';
    }
  };

  return (
      <header className="h-16 border-b border-neutral-200 bg-white flex items-center justify-between px-6 sticky top-0 z-50 shadow-sm">
        <div className="flex items-center gap-3">
          <h1 className="text-neutral-900">TeamMaker</h1>
          <Button variant="ghost" size="icon" onClick={onHelpClick}>
            <HelpCircle className="size-5 text-neutral-500" />
          </Button>
        </div>

        <Button variant="outline" size="sm" onClick={handleLogout} className="gap-2">
          <LogOut className="size-4" />
          로그아웃
        </Button>
      </header>
  );
}
