import { useState } from 'react';
import type { DragEvent } from 'react';
import { Plus, RotateCcw, X, Trash2 } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from './ui/card.tsx';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from './ui/table.tsx';
import { Button } from './ui/button.tsx';
import { Input } from './ui/input.tsx';
import type { Streamer, TableData, Position } from "../Home";
import type { TeamBoardDto } from "../api/teamBoardApi.ts";

export async function fetchMyTeamBoards(): Promise<TeamBoardDto[]> {
  const token = localStorage.getItem("accessToken");

  if (!token) {
    throw new Error("로그인 정보가 없습니다. accessToken 없음");
  }

  const res = await fetch("/api/teamBoards/my", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) {
    const msg = await res.text();
    throw new Error(msg || "팀보드 불러오기 실패");
  }

  return res.json();
}

interface TableGridProps {
  tables: TableData[];
  selectedTableId: string | null;
  selectedStreamer: Streamer | null;
  tableListSelections: Record<string, string>;
  onSelectTable: (id: string) => void;
  onApplyStreamerList: (tableId: string, listId: string) => void;
  onResetTable: (tableId: string) => void;
  onRemovePlayer: (tableId: string, position: Position) => void;
  onAddTable: () => void;
  onUpdateTableName: (tableId: string, newName: string) => void;
  onDeleteTable: (tableId: string) => void;
  onSelectStreamerByName: (name: string) => void;
  onDragOver: (e: DragEvent<HTMLDivElement>) => void;
  onDrop: (tableId: string) => (e: DragEvent<HTMLDivElement>) => void;
}

const positions: Position[] = ['Top', 'Jungle', 'Mid', 'Bottom', 'Support'];

export function TableGrid({
                            tables,
                            selectedTableId,
                            //selectedStreamer,
                            //tableListSelections,
                            onSelectTable,
                            //onApplyStreamerList,
                            onResetTable,
                            onRemovePlayer,
                            onAddTable,
                            onUpdateTableName,
                            onDeleteTable,
                            onSelectStreamerByName,
                            onDragOver,
                            onDrop,
                          }: TableGridProps) {
  const [editingTableId, setEditingTableId] = useState<string | null>(null);
  const [editingTableName, setEditingTableName] = useState<string>('');

  const handleStartEdit = (table: TableData) => {
    setEditingTableId(table.id);
    setEditingTableName(table.name);
  };

  const handleFinishEdit = (tableId: string) => {
    onUpdateTableName(tableId, editingTableName);
    setEditingTableId(null);
  };

  return (
      <section className="h-full flex flex-col overflow-hidden">
        <div className="flex items-center justify-between p-6 pb-4 flex-shrink-0">
          <h2 className="text-neutral-900">테이블 목록</h2>
          <Button onClick={onAddTable} variant="outline" size="sm">
            <Plus className="size-4 mr-2" />
            테이블 추가
          </Button>
        </div>

        <div className="flex gap-6 overflow-x-auto overflow-y-auto px-6 pb-12 pt-4">

        {tables.length === 0 && (
              <div className="col-span-2 text-center text-neutral-400 py-8">
                등록된 테이블이 없습니다.
              </div>
          )}

          {tables.map((table) => {
            const isSelected = selectedTableId === table.id;
            const totalScore = Object.values(table.positions).reduce(
                (sum, p) => sum + (p?.score ?? 0),
                0,
            );

            return (
                <Card
                    key={table.id}
                    className={`shadow-md transition-all cursor-pointer min-w-[370px] flex-shrink-0 p-1 min-h-[390px] ${
                        isSelected ? 'ring-2 ring-blue-500' : ''
                    }`}
                    onClick={() => onSelectTable(table.id)}
                    onDragOver={onDragOver}
                    onDrop={onDrop(table.id)}
                >
                  <CardHeader className="border-b border-neutral-200 bg-neutral-50 space-y-1 py-2 px-3">

                  <div className="flex items-center justify-between gap-2">
                      {/* 테이블 이름 (왼쪽) */}
                      <div className="flex-1 min-w-0">
                        <CardTitle
                            className="text-neutral-900 cursor-text truncate"
                            onClick={(e) => {
                              e.stopPropagation();
                              handleStartEdit(table);
                            }}
                        >
                          {editingTableId === table.id ? (
                              <Input
                                  value={editingTableName}
                                  autoFocus
                                  onChange={(e) => setEditingTableName(e.target.value)}
                                  onBlur={() => handleFinishEdit(table.id)}
                                  onKeyDown={(e) => {
                                    if (e.key === 'Enter') {
                                      handleFinishEdit(table.id);
                                    }
                                    if (e.key === 'Escape') {
                                      setEditingTableId(null);
                                      setEditingTableName(table.name);
                                    }
                                  }}
                                  onClick={(e) => e.stopPropagation()}
                                  className="h-8"
                              />
                          ) : (
                              table.name
                          )}
                        </CardTitle>
                      </div>

                      {/* 버튼들 (오른쪽) */}
                      <div className="flex items-center gap-2 flex-shrink-0">
                        <Button
                            variant="outline"
                            size="sm"
                            onClick={(e) => {
                              e.stopPropagation();
                              onResetTable(table.id);
                            }}
                        >
                          <RotateCcw className="size-4" />
                        </Button>
                        <Button
                            variant="outline"
                            size="sm"
                            onClick={(e) => {
                              e.stopPropagation();
                              onDeleteTable(table.id);
                            }}
                            className="text-red-500 hover:text-red-600"
                        >
                          <Trash2 className="size-4" />
                        </Button>
                      </div>
                    </div>
                  </CardHeader>


                  <CardContent className="p-0">
                    <Table>
                      <TableHeader>
                        <TableRow>
                          <TableHead>Position</TableHead>
                          <TableHead>Player</TableHead>
                          <TableHead>Score</TableHead>
                          <TableHead className="w-16"></TableHead>
                        </TableRow>
                      </TableHeader>
                      <TableBody>
                        {positions.map((position) => {
                          const player = table.positions[position];
                          return (
                              <TableRow
                                  key={position}
                                  className={player ? 'cursor-pointer hover:bg-neutral-50' : ''}
                                  onClick={(e) => {
                                    if (player) {
                                      e.stopPropagation();
                                      onSelectStreamerByName(player.name);
                                    }
                                  }}
                              >
                                <TableCell className="font-medium">{position}</TableCell>
                                <TableCell>
                                  {player ? (
                                      <span className="text-blue-600">
                                {player.name}
                              </span>
                                  ) : (
                                      <span className="text-neutral-400">-</span>
                                  )}
                                </TableCell>
                                <TableCell>{player ? player.score : '-'}</TableCell>
                                <TableCell>
                                  {player && (
                                      <Button
                                          size="icon"
                                          variant="ghost"
                                          onClick={(e) => {
                                            e.stopPropagation();
                                            onRemovePlayer(table.id, position);
                                          }}
                                          className="size-8"
                                      >
                                        <X className="size-4 text-red-500" />
                                      </Button>
                                  )}
                                </TableCell>
                              </TableRow>
                          );
                        })}
                        <TableRow className="bg-neutral-50">
                          <TableCell className="font-medium">Total</TableCell>
                          <TableCell>-</TableCell>
                          <TableCell className="font-medium">{totalScore}</TableCell>
                          <TableCell></TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </CardContent>
                </Card>
            );
          })}
        </div>
      </section>
  );
}
