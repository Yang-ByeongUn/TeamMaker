import {HelpCircle, LogOut} from 'lucide-react';
import { Button } from './ui/button.tsx';
import { logoutRequest } from '../api/authApi';

interface HeaderProps {
  onHelpClick: () => void;
}

export function Header({ onHelpClick }: HeaderProps) {
  const handleLogout = async () => {
    if (confirm('로그아웃 하시겠습니까?')) {
      try {
        await logoutRequest();

        // 클라이언트 토큰 제거
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");

        // 로그인 페이지로 이동
        window.location.href = "/login";
      } catch (error) {
        console.error("로그아웃 실패:", error);
      }
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
