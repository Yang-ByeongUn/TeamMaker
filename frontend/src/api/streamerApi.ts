// src/api/streamerApi.ts
import type { Position } from '../Home';

export interface StreamerDto {
  id: string;
  streamerName: string;
  score: number;
  position: string;
}

export interface CreateStreamerRequest {
  streamerName: string;
  score: number;
  position: string; // enum 이름 (예: "TOP")
}

const BASE_URL = '/api/streamers';

export async function fetchStreamers(): Promise<StreamerDto[]> {
  const res = await fetch(`${BASE_URL}?size=20`);

  if (!res.ok) {
    const text = await res.text();
    throw new Error('스트리머 목록 조회 실패: ' + text);
  }

  const data = await res.json();

  if (Array.isArray(data.contents)) return data.contents;
  if (Array.isArray(data.content)) return data.content;
  if (Array.isArray(data.items)) return data.items;

  console.error('예상과 다른 응답:', data);
  throw new Error('스트리머 목록 응답 형식 오류');
}

export async function createStreamer(
    name: string,
    position: Position,
    score: number,
): Promise<void> {
  const payload: CreateStreamerRequest = {
    streamerName: name,
    score,
    position: position.toUpperCase(), // "Top" -> "TOP"
  };

  const res = await fetch(`${BASE_URL}/save`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    const text = await res.text();
    throw new Error('스트리머 저장 실패: ' + text);
  }
}

export async function deleteStreamer(id: string): Promise<void> {
  const res = await fetch(`${BASE_URL}/${id}`, {
    method: 'DELETE',
  });
  if (!res.ok) {
    const text = await res.text();
    throw new Error('스트리머 삭제 실패: ' + text);
  }
}
