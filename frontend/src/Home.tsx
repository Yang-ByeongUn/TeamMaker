import { useEffect, useState } from "react";
import * as React from "react";
import type { DragEvent } from "react";
import { Header } from "./components/Header";
import { Sidebar } from "./components/Sidebar";
import { TableGrid } from "./components/TableGrid";
import { StreamerDetail } from "./components/StreamerDetail";
import { HelpModal } from "./components/HelpModal";
import { AddStreamerModal } from "./components/AddStreamerModal";
import {
  fetchMyTeamBoards,
  type TeamBoardDto,
  type TeamBoardUpdateDto,
  createTeamBoard,
  updateTeamBoard,
  deleteTeamBoardApi,
} from "./api/teamBoardApi";

import {
  createStreamer,
  fetchStreamers,
  deleteStreamer,
  type StreamerDto,
} from "./api/streamerApi";

export type Position = "Top" | "Jungle" | "Mid" | "Bottom" | "Support";

export type Player = {
  id?: string;
  name: string;
  score: number;
};

export type TableData = {
  id: string;
  name: string;
  positions: Partial<Record<Position, Player>>;
};

export type Streamer = {
  id: string;
  name: string;
  position: Position;
  rank: string;
  winRate: string;
  score: number;
  tableLists: TableData[];
};

const SCORE_MAX = 100;

function mapDtoToTableData(
    dto: TeamBoardDto,
    streamerMap: Record<string, Streamer>,
): TableData {
  const positions: TableData["positions"] = {};

  const assign = (pos: Position, id: string | null) => {
    if (!id) return;
    const s = streamerMap[id];
    if (!s) return;
    positions[pos] = {
      id: s.id,
      name: s.name,
      score: s.score,
    };
  };

  assign("Top", dto.topId);
  assign("Jungle", dto.jgId);
  assign("Mid", dto.midId);
  assign("Bottom", dto.adId);
  assign("Support", dto.supId);

  return {
    id: dto.id,
    name: dto.title,
    positions,
  };
}

function buildUpdateDtoFromTable(table: TableData): TeamBoardUpdateDto {
  return {
    teamBoardId: table.id,
    title: table.name,
    topId: table.positions.Top?.id ?? null,
    jgId: table.positions.Jungle?.id ?? null,
    midId: table.positions.Mid?.id ?? null,
    adId: table.positions.Bottom?.id ?? null,
    supId: table.positions.Support?.id ?? null,
  };
}

export default function App() {
  const [streamers, setStreamers] = useState<Streamer[]>([]);
  const [selectedStreamerId, setSelectedStreamerId] = useState<string>("");

  const [tables, setTables] = useState<TableData[]>([]);
  const [selectedTableId, setSelectedTableId] = useState<string | null>(null);

  const [searchQuery, setSearchQuery] = useState("");
  const [minScore, setMinScore] = useState<number>(0);
  const [maxScore, setMaxScore] = useState<number>(SCORE_MAX);

  const [draggingStreamerId, setDraggingStreamerId] = useState<string | null>(
      null,
  );
  const [tableListSelections, setTableListSelections] =
      useState<Record<string, string>>({});

  const [isHelpOpen, setIsHelpOpen] = useState(false);
  const [isAddStreamerOpen, setIsAddStreamerOpen] = useState(false);

  const [tableAreaHeight, setTableAreaHeight] = useState(60);
  const [isResizing, setIsResizing] = useState(false);

  const selectedStreamer =
      streamers.find((s) => s.id === selectedStreamerId) ?? null;

  // ✅ 최초 로딩: 스트리머 불러오고 → 내 팀보드 불러오기
  useEffect(() => {
    const load = async () => {
      try {
        // 1) 스트리머 목록
        const list: StreamerDto[] = await fetchStreamers();
        const mapped: Streamer[] = list.map((s) => ({
          id: s.id,
          name: s.streamerName,
          position: s.position as Position,
          rank: "",
          winRate: "0%",
          score: s.score,
          tableLists: [],
        }));

        setStreamers(mapped);

        if (mapped.length === 0) {
          setSelectedStreamerId("");
          setTables([]);
          setSelectedTableId(null);
          return;
        }

        // 첫 번째 스트리머 선택
        setSelectedStreamerId(mapped[0].id);

        // 2) 팀보드 로딩 (로그인한 유저 기준)
        const streamerMap: Record<string, Streamer> = {};
        mapped.forEach((s) => {
          streamerMap[s.id] = s;
        });

        const dtos: TeamBoardDto[] = await fetchMyTeamBoards();
        const tb = dtos.map((dto) => mapDtoToTableData(dto, streamerMap));

        setTables(tb);
        setSelectedTableId(tb[0]?.id ?? null);
        setTableListSelections({});
      } catch (e) {
        console.error(e);
        alert("스트리머/팀보드를 불러오지 못했습니다.");
      }
    };

    load();
  }, []);

  const filteredStreamers = streamers.filter((s) => {
    const matchSearch = s.name
    .toLowerCase()
    .includes(searchQuery.toLowerCase());
    const matchScore = s.score >= minScore && s.score <= maxScore;
    return matchSearch && matchScore;
  });

  const handleViewDetails = (streamer: Streamer) => {
    setSelectedStreamerId(streamer.id);
  };

  const handleDeleteStreamer = async (id: string) => {
    try {
      await deleteStreamer(id);
      setStreamers((prev) => {
        const next = prev.filter((s) => s.id !== id);
        if (next.length === 0) {
          setSelectedStreamerId("");
          return [];
        }
        if (selectedStreamerId === id) {
          setSelectedStreamerId(next[0].id);
        }
        return next;
      });
    } catch (e) {
      console.error(e);
      alert("스트리머 삭제에 실패했습니다.");
    }
  };

  const handleAddStreamer = async (
      name: string,
      position: Position,
      _rank: string,
      score: number,
  ) => {
    try {
      await createStreamer(name, position, score);
    } catch (e) {
      console.error(e);
      alert("스트리머 추가에 실패했습니다.");
      return;
    }

    try {
      const list = await fetchStreamers();

      const mapped: Streamer[] = list.map((s) => ({
        id: s.id,
        name: s.streamerName,
        position: s.position as Position,
        rank: "",
        winRate: "0%",
        score: s.score,
        tableLists: [],
      }));

      setStreamers(mapped);

      if (mapped.length > 0) {
        setSelectedStreamerId(mapped[mapped.length - 1].id);
      }
    } catch (e) {
      console.error("스트리머는 저장됐는데 목록 갱신에 실패했습니다.", e);
    }
  };

  // 드래그 앤 드롭
  const handleStreamerDragStart =
      (id: string) => (e: DragEvent<HTMLDivElement>) => {
        setDraggingStreamerId(id);
        e.dataTransfer.effectAllowed = "move";
      };

  const handleStreamerDragEnd = () => {
    setDraggingStreamerId(null);
  };

  const handleTableDragOver = (e: DragEvent<HTMLDivElement>) => {
    e.preventDefault();
  };

  const handleTableDrop =
      (tableId: string) => async (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        if (!draggingStreamerId) return;

        const streamer = streamers.find((s) => s.id === draggingStreamerId);
        if (!streamer) return;

        let updatedTable: TableData | undefined;

        setTables((prev) =>
            prev.map((table) => {
              if (table.id !== tableId) return table;
              const newTable: TableData = {
                ...table,
                positions: {
                  ...table.positions,
                  [streamer.position]: {
                    id: streamer.id,
                    name: streamer.name,
                    score: streamer.score,
                  },
                },
              };
              updatedTable = newTable;
              return newTable;
            }),
        );

        setSelectedTableId(tableId);
        setDraggingStreamerId(null);

        if (updatedTable) {
          try {
            await updateTeamBoard(buildUpdateDtoFromTable(updatedTable));
          } catch (e) {
            console.error(e);
          }
        }
      };

  // 테이블 리스트 적용
  const handleApplyStreamerList = async (tableId: string, listId: string) => {
    if (listId === "default") {
      await handleResetTable(tableId);
      return;
    }

    if (!selectedStreamer) return; // 🔴 null 방어

    const template = selectedStreamer.tableLists.find((l) => l.id === listId);
    if (!template) return;

    let updatedTable: TableData | undefined;

    setTables((prev) =>
        prev.map((t) =>
            t.id === tableId
                ? (() => {
                  const newTable: TableData = {
                    ...t,
                    positions: { ...template.positions },
                  };
                  updatedTable = newTable;
                  return newTable;
                })()
                : t,
        ),
    );

    setTableListSelections((prev) => ({
      ...prev,
      [tableId]: listId,
    }));

    if (updatedTable) {
      try {
        await updateTeamBoard(buildUpdateDtoFromTable(updatedTable));
      } catch (err) {
        console.error(err);
      }
    }
  };

  const handleResetTable = async (tableId: string) => {
    let updatedTable: TableData | undefined;

    setTables((prev) =>
        prev.map((t) =>
            t.id === tableId
                ? (() => {
                  const newTable: TableData = { ...t, positions: {} };
                  updatedTable = newTable;
                  return newTable;
                })()
                : t,
        ),
    );

    setTableListSelections((prev) => ({
      ...prev,
      [tableId]: "default",
    }));

    if (updatedTable) {
      try {
        await updateTeamBoard(buildUpdateDtoFromTable(updatedTable));
      } catch (err) {
        console.error(err);
      }
    }
  };

  const handleRemoveFromTable = async (
      tableId: string,
      position: Position,
  ) => {
    let updatedTable: TableData | undefined;

    setTables((prev) =>
        prev.map((table) => {
          if (table.id === tableId) {
            const newPositions = { ...table.positions };
            delete newPositions[position];
            const newTable: TableData = {
              ...table,
              positions: newPositions,
            };
            updatedTable = newTable;
            return newTable;
          }
          return table;
        }),
    );

    if (updatedTable) {
      try {
        await updateTeamBoard(buildUpdateDtoFromTable(updatedTable));
      } catch (e) {
        console.error(e);
      }
    }
  };

  const handleAddTable = async () => {
    const name = `Team ${tables.length + 1}`;

    try {
      const newId = await createTeamBoard({
        title: name,
      });

      const newTable: TableData = {
        id: newId,
        name,
        positions: {},
      };

      setTables((prev) => [...prev, newTable]);
      setSelectedTableId(newId);
    } catch (e) {
      console.error(e);
      alert("테이블 생성에 실패했습니다.");
    }
  };

  const handleUpdateTableName = async (
      tableId: string,
      newName: string,
  ) => {
    let updatedTable: TableData | undefined;

    setTables((prev) =>
        prev.map((t) =>
            t.id === tableId
                ? (() => {
                  const newTable: TableData = {
                    ...t,
                    name: newName.trim() || t.name,
                  };
                  updatedTable = newTable;
                  return newTable;
                })()
                : t,
        ),
    );

    if (updatedTable) {
      try {
        await updateTeamBoard(buildUpdateDtoFromTable(updatedTable));
      } catch (e) {
        console.error(e);
      }
    }
  };

  const handleDeleteTable = async (tableId: string) => {
    try {
      await deleteTeamBoardApi(tableId);

      setTables((prev) => {
        const next = prev.filter((t) => t.id !== tableId);
        if (selectedTableId === tableId) {
          setSelectedTableId(next[0]?.id ?? null);
        }
        return next;
      });
    } catch (e) {
      console.error(e);
      alert("테이블 삭제에 실패했습니다.");
    }
  };

  const handleSelectStreamerByName = (name: string) => {
    const streamer = streamers.find((s) => s.name === name);
    if (streamer) {
      setSelectedStreamerId(streamer.id);
    }
  };

  const selectedTable =
      tables.find((t) => t.id === selectedTableId) ?? tables[0] ?? null;

  const handleMouseDown = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsResizing(true);
    const currentRef = e.currentTarget;
    const currentRefY = currentRef.getBoundingClientRect().top;

    const onMouseMove = (e: MouseEvent) => {
      const newHeight = ((e.clientY - currentRefY) / window.innerHeight) * 100;
      if (newHeight > 20 && newHeight < 80) {
        setTableAreaHeight(newHeight);
      }
    };

    const onMouseUp = () => {
      setIsResizing(false);
      document.removeEventListener("mousemove", onMouseMove);
      document.removeEventListener("mouseup", onMouseUp);
    };

    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);
  };

  return (
      <div className="h-screen bg-neutral-50 overflow-hidden flex flex-col">
        <Header onHelpClick={() => setIsHelpOpen(true)} />

        <div className="flex flex-1 overflow-hidden">
          <Sidebar
              streamers={filteredStreamers}
              selectedStreamerId={selectedStreamerId}
              searchQuery={searchQuery}
              onSearchChange={setSearchQuery}
              minScore={minScore}
              maxScore={maxScore}
              onMinScoreChange={setMinScore}
              onMaxScoreChange={setMaxScore}
              onViewDetails={handleViewDetails}
              onDeleteStreamer={handleDeleteStreamer}
              onAddStreamer={() => setIsAddStreamerOpen(true)}
              onDragStart={handleStreamerDragStart}
              onDragEnd={handleStreamerDragEnd}
              scoreMax={SCORE_MAX}
          />

          <main className="flex-1 ml-80 flex flex-col overflow-hidden">
            <div
                className="overflow-hidden flex flex-col"
                style={{ height: `${tableAreaHeight}vh` }}
            >
              <TableGrid
                  tables={tables}
                  selectedTableId={selectedTableId}
                  selectedStreamer={selectedStreamer}
                  tableListSelections={tableListSelections}
                  onSelectTable={setSelectedTableId}
                  onApplyStreamerList={handleApplyStreamerList}
                  onResetTable={handleResetTable}
                  onRemovePlayer={handleRemoveFromTable}
                  onAddTable={handleAddTable}
                  onUpdateTableName={handleUpdateTableName}
                  onDeleteTable={handleDeleteTable}
                  onSelectStreamerByName={handleSelectStreamerByName}
                  onDragOver={handleTableDragOver}
                  onDrop={handleTableDrop}
              />
            </div>

            <div
                onMouseDown={handleMouseDown}
                className={`h-2 bg-neutral-200 hover:bg-blue-400 cursor-row-resize flex-shrink-0 transition-colors ${
                    isResizing ? "bg-blue-500" : ""
                }`}
            />

            {selectedStreamer && selectedTable && (
                <div className="flex-1 overflow-hidden bg-white border-t border-neutral-200 shadow-lg">
                  <div className="p-8 h-full overflow-y-auto">
                    <StreamerDetail
                        streamer={selectedStreamer}
                        selectedTable={selectedTable}
                    />
                  </div>
                </div>
            )}
          </main>
        </div>

        <HelpModal
            isOpen={isHelpOpen}
            onClose={() => setIsHelpOpen(false)}
        />
        <AddStreamerModal
            isOpen={isAddStreamerOpen}
            onClose={() => setIsAddStreamerOpen(false)}
            onAdd={handleAddStreamer}
            scoreMax={SCORE_MAX}
        />
      </div>
  );
}
