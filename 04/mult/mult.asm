// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// Copy register 0 to register 3 to use as loop variable
@R0
D=M
@R3
M=D

// Initialize product to 0
	@0
	D=A
	@R2
	M=D

(MULTIPLY)
		// Check if we need to do more loops
		// if (counter == 0) { jump } else counter--
	@R3
	D=M
	@END
	D;JEQ
	@R3
	D=D-1
	M=D

		// Multiply (add register R1 once to R2
	@R1
	D=M
	@R2
	D=D+M
	M=D
	@MULTIPLY // Go to start of loop again
	0;JMP

(END)
	@END
	0;JMP