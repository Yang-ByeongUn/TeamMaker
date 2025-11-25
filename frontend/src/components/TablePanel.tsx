import { Card, CardContent, CardHeader, CardTitle } from './ui/card.tsx';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from './ui/table.tsx';
import { Button } from './ui/button.tsx';
import { X } from 'lucide-react';
import type {TableData} from '../Home.tsx';

interface TablePanelProps {
  table: TableData;
  isSelected: boolean;
  onSelect: () => void;
  onRemovePlayer: (tableId: string, position: keyof TableData['positions']) => void;
}

export function TablePanel({ table, isSelected, onSelect, onRemovePlayer }: TablePanelProps) {
  const positions: (keyof TableData['positions'])[] = ['Top', 'Jungle', 'Mid', 'Bottom', 'Support'];

  return (
    <Card 
      className={`shadow-md cursor-pointer transition-all ${
        isSelected ? 'ring-2 ring-blue-500' : ''
      }`}
      onClick={onSelect}
    >
      <CardHeader className="border-b border-neutral-200 bg-neutral-50">
        <CardTitle className="text-neutral-900 flex items-center justify-between">
          {table.name}
          {isSelected && (
            <span className="px-2 py-1 rounded bg-blue-500 text-white text-xs">Selected</span>
          )}
        </CardTitle>
      </CardHeader>
      <CardContent className="p-0">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Position</TableHead>
              <TableHead>Player</TableHead>
              <TableHead>Score</TableHead>
              <TableHead>Win Rate</TableHead>
              <TableHead className="w-16">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {positions.map((position) => {
              const player = table.positions[position];
              return (
                <TableRow key={position}>
                  <TableCell className="font-medium">{position}</TableCell>
                  <TableCell>
                    {player ? player.name : <span className="text-neutral-400">Empty</span>}
                  </TableCell>
                  <TableCell>
                    {player ? player.score : '-'}
                  </TableCell>
                  <TableCell>
                    {player ? 95 : '-'} //추후 수정 필요
                  </TableCell>
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
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
}
