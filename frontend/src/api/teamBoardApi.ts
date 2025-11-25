// src/api/teamBoardApi.ts
export interface TeamBoardDto {
  id: string;
  userId: string;
  title: string;
  topId: string | null;
  jgId: string | null;
  midId: string | null;
  adId: string | null;
  supId: string | null;
  sumScore: number;
}

export interface TeamBoardSaveDto {
  title: string;
}

export interface TeamBoardUpdateDto {
  teamBoardId: string;
  title: string;
  topId: string | null;
  jgId: string | null;
  midId: string | null;
  adId: string | null;
  supId: string | null;
}

const BASE_URL = "/api/teamBoards";
const ACCESS_TOKEN_KEY = "accessToken";

function getAuthHeaders(): HeadersInit {
  const token = localStorage.getItem(ACCESS_TOKEN_KEY);

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  return headers;
}

export async function fetchMyTeamBoards(): Promise<TeamBoardDto[]> {
  const res = await fetch(`${BASE_URL}/me`, {
    method: "GET",
    headers: getAuthHeaders(),
  });
  if (!res.ok) {
    console.error("fetchMyTeamBoards failed", res.status, await res.text());
    throw new Error("팀 보드 목록을 불러오지 못했습니다.");
  }
  return res.json();
}


export async function createTeamBoard(
    dto: TeamBoardSaveDto,
): Promise<string> {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(dto),
  });

  if (!res.ok) {
    console.error("createTeamBoard failed", res.status, await res.text());
    throw new Error("팀 보드를 생성하지 못했습니다.");
  }
  const id: string = await res.json();
  return id;
}

export async function updateTeamBoard(
    dto: TeamBoardUpdateDto,
): Promise<void> {
  const res = await fetch(`${BASE_URL}/${dto.teamBoardId}`, {
    method: "PUT",
    headers: getAuthHeaders(),
    body: JSON.stringify(dto),
  });
  if (!res.ok) {
    console.error("updateTeamBoard failed", res.status, await res.text());
    throw new Error("팀 보드를 수정하지 못했습니다.");
  }
}

export async function deleteTeamBoardApi(
    teamBoardId: string,
): Promise<void> {
  const res = await fetch(`${BASE_URL}/${teamBoardId}`, {
    method: "DELETE",
    headers: getAuthHeaders(),
  });
  if (!res.ok) {
    console.error("deleteTeamBoardApi failed", res.status, await res.text());
    throw new Error("팀 보드를 삭제하지 못했습니다.");
  }
}
