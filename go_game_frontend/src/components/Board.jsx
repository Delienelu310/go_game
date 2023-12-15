
import { useRef, useEffect } from "react";

export default function Board({ size, cellSize, boardMatrix, sendMove, color }){
    const canvasRef = useRef(null);

    const handleCanvasClick = (event) => {
        const canvas = canvasRef.current;
        const rect = canvas.getBoundingClientRect();
        const cellSize = canvas.width / size;
    
        const clickX = event.clientX - rect.left;
        const clickY = event.clientY - rect.top;
        
        const col = Math.floor(clickX / cellSize);
        const row = Math.floor(clickY / cellSize);
    
        // moves.push({x: row, y: col, color: color});
        boardMatrix[col][row] = color;
        refresh();
    };

    function refresh(){
        const canvas = canvasRef.current;
        const context = canvas.getContext('2d');
    
        // Clear the canvas
        context.clearRect(0, 0, canvas.width, canvas.height);

        //background
        context.fillStyle = '#f4e8d1'; 
        context.fillRect(0, 0, canvas.width, canvas.height);
    
        // Draw the Go board
        context.strokeStyle = '#000';
        for (let i = 0; i < size; i++) {
        const linePosition = i * cellSize;
        context.beginPath();
        context.moveTo(linePosition + 0.5 * cellSize, cellSize * 0.5);
        context.lineTo(linePosition + 0.5 * cellSize, canvas.height - cellSize * 0.5);
        context.stroke();
    
        context.beginPath();
        context.moveTo(0.5 * cellSize, linePosition + 0.5 * cellSize);
        context.lineTo(canvas.width - cellSize * 0.5, linePosition + 0.5 * cellSize );
        context.stroke();
        }
    
        // Draw stones
        
        boardMatrix.forEach((row, r) => {
            row.forEach( (cell, c) => {
                if(!cell) return;
                context.fillStyle = cell;
                const x = r * cellSize + cellSize / 2;
                const y = c * cellSize + cellSize / 2;
            
                context.beginPath();
                context.arc(x, y, cellSize / 3, 0, 2 * Math.PI);
                context.fill();
            });
        });
        
        context.fillStyle = '#000';
    }

        
    
    useEffect( refresh, [size, boardMatrix]);
      
    return (
        <canvas 
            ref={canvasRef} 
            width={cellSize * size} 
            height={cellSize * size} 
            style={{ border: '1px solid #000' }} 
            onClick={handleCanvasClick}
        />
    );
};