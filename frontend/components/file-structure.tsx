"use client";
import { useState } from "react";
import { ChevronRight, ChevronDown, File, Folder } from "lucide-react";

// Tipo para um nó de estrutura de arquivos, pode ser uma string ou um objeto de nós filhos
type FileStructureNode = string | { [key: string]: FileStructureNode[] };

// Alteração na tipagem para aceitar o tipo correto de estrutura
interface FileStructureProps {
  structure: FileStructureNode; // Alterado de 'string' para 'FileStructureNode'
}

export function FileStructure({ structure }: FileStructureProps) {
  return <FileNode node={structure} />;
}

function FileNode({ node }: { node: FileStructureNode }) {
  const [isOpen, setIsOpen] = useState(false);

  // Função recursiva para renderizar a estrutura de arquivos
  const renderNode = (nodeData: FileStructureNode) => {
    if (typeof nodeData === "string") {
      return (
        <div className="flex items-center">
          <File className="w-4 h-4 mr-2" />
          {nodeData}
        </div>
      );
    }

    if (typeof nodeData === "object") {
      return Object.entries(nodeData).map(([key, value]) => (
        <FolderNode key={key} name={key} children={value} renderNode={renderNode} />
      ));
    }

    return null;
  };

  return renderNode(node);
}

function FolderNode({
  name,
  children,
  renderNode, // Passando renderNode como prop
}: {
  name: string;
  children: FileStructureNode;
  renderNode: (nodeData: FileStructureNode) => JSX.Element | null; // Tipo correto para renderNode
}) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div>
      <div
        className="flex items-center cursor-pointer"
        onClick={() => setIsOpen(!isOpen)}
      >
        {isOpen ? (
          <ChevronDown className="w-4 h-4 mr-2" />
        ) : (
          <ChevronRight className="w-4 h-4 mr-2" />
        )}
        <Folder className="w-4 h-4 mr-2" />
        {name}
      </div>
      {isOpen && (
        <div className="ml-4">
          {Array.isArray(children)
            ? children.map((child, index) => (
                <FileNode key={`${name}-${index}`} node={child} />
              ))
            : renderNode(children)}
        </div>
      )}
    </div>
  );
}
