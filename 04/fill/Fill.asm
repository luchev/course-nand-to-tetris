// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// Set screen first register
	@SCREEN
	D=A
	@begin
	M=D

// Set screen last register
	@24575
	D=A
	@end
	M=D
	
	@CHECK_KEYBOARD
	D=A
	@jump_after_reset
	M=D
	

(RESET_COUNTER)
	@SCREEN
	D=A
	@counter
	M=D
	@jump_after_reset
	D=M
	A=D
	0;JMP

(CHECK_KEYBOARD)
	@24576
	D=M
	@BLACK_SCREEN
	D;JGT
	@WHITE_SCREEN
	0;JMP

(BLACK_SCREEN)
	@BLACK_LOOP
	D=A
	@jump_after_reset
	M=D
	@RESET_COUNTER
	0;JMP

(BLACK_LOOP)
	@end
	D=M
	@counter
	D=D-M
	@EXIT_BLACK_LOOP
	D+1;JEQ
	
	@counter
	A=M
	D=-1
	M=D
	
	@counter
	D=M
	D=D+1
	M=D
	@BLACK_LOOP
	0;JMP
	
(EXIT_BLACK_LOOP)
	@CHECK_KEYBOARD
	0;JMP

(WHITE_SCREEN)
	@WHITE_LOOP
	D=A
	@jump_after_reset
	M=D
	@RESET_COUNTER
	0;JMP
	
(WHITE_LOOP)
	@end
	D=M
	@counter
	D=D-M
	@EXIT_WHITE_LOOP
	D+1;JEQ
	
	@counter
	A=M
	D=0
	M=D
	
	@counter
	D=M
	D=D+1
	M=D
	@WHITE_LOOP
	0;JMP
	
(EXIT_WHITE_LOOP)
	@CHECK_KEYBOARD
	0;JMP
