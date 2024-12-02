"use client";

import { useEffect, useRef } from "react";
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

  useEffect(() => {
    if (mermaidRef.current) {
      try {
        const cleanedChart = cleanMermaidCode(chart);

        if (mermaid) {
          if (!mermaid.mermaidAPI) {
            console.error("Mermaid API não está disponível.");
            return;
          }

          mermaid.initialize({ startOnLoad: false });

          const uniqueId = `mermaid-${Math.random().toString(36).substr(2, 9)}`;

          mermaid.render(uniqueId, cleanedChart).then((result) => {
            if (mermaidRef.current) {
              mermaidRef.current.innerHTML = result.svg;
            }
          }).catch((error) => {
            console.error("Erro ao renderizar o gráfico Mermaid:", error);
          });
        } else {
          console.error("Mermaid não foi carregado corretamente.");
        }
      } catch (error) {
        console.error("Erro ao processar o gráfico:", error);
      }
    }
  }, [chart]);

  return <div ref={mermaidRef} />;
}
