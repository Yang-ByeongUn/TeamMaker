import { useState } from "react";
import "./Login.css";

type LoginProps = {
  onLogin: () => void;
};

// 백엔드 Position enum 값과 맞춰 줄 것
type PositionType = "TOP" | "JUNGLE" | "MID" | "BOTTOM" | "SUPPORT";

export default function Login({ onLogin }: LoginProps) {
  const [mode, setMode] = useState<"login" | "signup">("login");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");

  // 🚀 회원가입용 추가 필드들
  const [streamerName, setStreamerName] = useState("");
  const [position, setPosition] = useState<PositionType>("MID");
  const [score, setScore] = useState<number>(0);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  // ================== 로그인 ==================
  const handleLogin = async () => {
    if (!username.trim() || !password.trim()) {
      setError("아이디와 비밀번호를 입력해주세요.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || "로그인에 실패했습니다.");
      }

      const data = await res.json();
      console.log("로그인 응답:", data);

      // ✅ 백엔드 응답 키에 맞게 정확히 매핑
      const accessToken: string | undefined = data.access_token;
      const refreshToken: string | undefined = data.refresh_token;

      if (!accessToken) {
        throw new Error("서버에서 access token을 받지 못했습니다.");
      }

      // ✅ 로컬스토리지에 진짜 JWT 문자열 저장
      localStorage.setItem("accessToken", accessToken);
      if (refreshToken) {
        localStorage.setItem("refreshToken", refreshToken);
      }
      localStorage.setItem("username", username);

      onLogin();
    } catch (e: any) {
      console.error(e);
      setError(e.message || "로그인 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  // ================== 회원가입 ==================
  const handleSignup = async () => {
    if (!username.trim() || !password.trim()) {
      setError("아이디와 비밀번호를 입력해주세요.");
      return;
    }
    if (password !== password2) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }
    if (!streamerName.trim()) {
      setError("스트리머 이름을 입력해주세요.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const res = await fetch("/api/auth/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username,
          password,
          streamerName,
          score,
          position, // "TOP" | "JUNGLE" | "MID" | "BOTTOM" | "SUPPORT"
        }),
      });

      if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || "회원가입에 실패했습니다.");
      }

      alert("회원가입 완료! 이제 로그인하세요.");
      setMode("login");
    } catch (e: any) {
      console.error(e);
      setError(e.message || "회원가입 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="page-wrapper">
        <div className="login-card">
          <h1 className="login-title">
            {mode === "login" ? "TeamMaker Login" : "Sign Up"}
          </h1>

          {/* 공통: 아이디 / 비밀번호 */}
          <input
              className="login-input"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
          />

          <input
              className="login-input"
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
          />

          {/* 회원가입 모드일 때만 추가 필드들 */}
          {mode === "signup" && (
              <>
                <input
                    className="login-input"
                    type="password"
                    placeholder="Confirm Password"
                    value={password2}
                    onChange={(e) => setPassword2(e.target.value)}
                />

                <input
                    className="login-input"
                    placeholder="Streamer Name"
                    value={streamerName}
                    onChange={(e) => setStreamerName(e.target.value)}
                />

                <input
                    className="login-input"
                    type="number"
                    placeholder="Score (예: 90)"
                    value={score}
                    onChange={(e) => setScore(Number(e.target.value) || 0)}
                />

                <select
                    className="login-input"
                    value={position}
                    onChange={(e) =>
                        setPosition(
                            e.target.value as
                                | "TOP"
                                | "JUNGLE"
                                | "MID"
                                | "BOTTOM"
                                | "SUPPORT",
                        )
                    }
                >
                  <option value="TOP">Top</option>
                  <option value="JUNGLE">Jungle</option>
                  <option value="MID">Mid</option>
                  <option value="BOTTOM">Bottom</option>
                  <option value="SUPPORT">Support</option>
                </select>
              </>
          )}

          {error && <div className="login-error">{error}</div>}

          {mode === "login" ? (
              <button
                  className="login-btn"
                  onClick={handleLogin}
                  disabled={loading}
              >
                {loading ? "로그인 중..." : "Login"}
              </button>
          ) : (
              <button
                  className="login-btn"
                  onClick={handleSignup}
                  disabled={loading}
              >
                {loading ? "가입 중..." : "Sign Up"}
              </button>
          )}

          <div className="login-footer">
            {mode === "login" ? (
                <>
                  아직 회원이 아니신가요?{" "}
                  <span onClick={() => setMode("signup")}>회원가입</span>
                </>
            ) : (
                <>
                  이미 계정이 있으신가요?{" "}
                  <span onClick={() => setMode("login")}>로그인</span>
                </>
            )}
          </div>
        </div>
      </div>
  );
}
