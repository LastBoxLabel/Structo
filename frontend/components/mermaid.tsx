'use client';

import { useEffect, useRef, useState } from "react";
import mermaid from "mermaid";

interface MermaidProps {
  chart: string;
}

function cleanMermaidCode(rawCode: string): string {
  return rawCode
    .replace(/```mermaid\s*/, "")
    .replace(/```$/, "")
    .trim();
}

export function Mermaid({ chart }: MermaidProps) {
  const mermaidRef = useRef<HTMLDivElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [scale, setScale] = useState(1);
  const [translate, setTranslate] = useState({ x: 0, y: 0 });

  useEffect(() => {
    if (mermaidRef.current) {
      try {
        const cleanedChart = cleanMermaidCode(chart);

        mermaid.initialize({ startOnLoad: false });

        const uniqueId = `mermaid-${Math.random().toString(36).substr(2, 9)}`;

        mermaid
          .render(uniqueId, cleanedChart)
          .then((result) => {
            if (mermaidRef.current) {
              mermaidRef.current.innerHTML = result.svg;

              const svgElement = mermaidRef.current.querySelector("svg");

              if (svgElement && containerRef.current) {
                const containerBounds = containerRef.current.getBoundingClientRect();
                const contentBounds = svgElement.getBoundingClientRect();

                const offsetX =
                  (containerBounds.width - contentBounds.width * scale) / 2;
                const offsetY =
                  (containerBounds.height - contentBounds.height * scale) / 2;

                setTranslate({
                  x: Math.max(offsetX, 0),
                  y: Math.max(offsetY, 0),
                });
              }
            }
          })
          .catch((error) => {
            console.error("Erro ao renderizar o gráfico Mermaid:", error);
          });
      } catch (error) {
        console.error("Erro ao processar o gráfico:", error);
      }
    }
  }, [chart, scale]);

  const handleZoomIn = () => {
    setScale((prev) => Math.min(prev + 0.1, 3));
  };

  const handleZoomOut = () => {
    setScale((prev) => Math.max(prev - 0.1, 0.5));
  };

  const handleResetZoom = () => {
    setScale(1);
  };

  return (
    <div className="relative">
      <div className="absolute right-2 top-2 z-10 flex gap-2">
        <button onClick={handleZoomIn} className="px-2 py-1 bg-gray-300 rounded">
          +
        </button>
        <button onClick={handleZoomOut} className="px-2 py-1 bg-gray-300 rounded">
          -
        </button>
        <button onClick={handleResetZoom} className="px-2 py-1 bg-gray-300 rounded">
          Reset
        </button>
      </div>
      <div
        ref={containerRef}
        className="overflow-auto border border-gray-300 rounded"
        style={{
          width: "100%",
          height: "400px",
          position: "relative",
        }}
      >
        <div
          ref={mermaidRef}
          style={{
            transform: `translate(${translate.x}px, ${translate.y}px) scale(${scale})`,
            transformOrigin: "top left",
          }}
        />
      </div>
    </div>
  );
}
