.data
  peg1:      .asciiz "peg 1"
  peg2:      .asciiz "peg 2"
  peg3:      .asciiz "peg 3"
  prompt:    .asciiz "Enter a number "
  message1:  .asciiz "Move disk from "
  message2:  .asciiz " to "
  newLine:   .asciiz "\n"
  n:         .word 0
.text
  .globl main
  main:
    # promt for user input
    li $v0, 4
    la $a0, prompt
    syscall 
    
    li $v0, 5
    syscall 
    sw $v0, n
    
    # pass variables to hanoi
    lw $t0, n                    # t0 is n
    la $a1, peg1
    la $a2, peg2
    la $a3, peg3
    
    # call hanoi function 
    jal hanoi
    
    # terminate program
    li $v0, 10
    syscall 
#-------------------------------------------
.globl hanoi        
hanoi:
  # store variables on the stack  
  addi $sp, $sp, -20
  sw $a1,   ($sp)
  sw $a2,  4($sp)    
  sw $a3,  8($sp)
  sw $t0, 12($sp)
  sw $ra, 16($sp)  
  
  #base case
  beq $t0, 0, hanoiDone 
  
  # n-1, re-organise arguments
  sub $t0, $t0, 1
  move $t2, $a3                #hold peg3 address
  move $a3, $a2
  move $a2, $t2 
  jal hanoi 
  
  # reorder arguments and print instructions
  jal printInstruction
  
  # reorder for second recursive call
  move $t2, $a1                 #hold peg1 address
  move $a1, $a2
  move $a2, $a3
  move $a3, $t2
  jal hanoi
   
  # restore registers and return
  jal hanoiDone
  jr $ra
  
  
  
.globl hanoiDone  
hanoiDone:
  lw $a1,   ($sp)
  lw $a2,  4($sp)    
  lw $a3,  8($sp)
  lw $t0, 12($sp)
  lw $ra, 16($sp)
  addi $sp, $sp, 20
  jr $ra
  
printInstruction:
  addi $sp, $sp, -4
  sw $ra, ($sp)
  
  li $v0, 4
  la $a0, message1
  syscall 
  
  li $v0, 4
  move $a0, $a1
  syscall 
  
  li $v0, 4
  la $a0, message2
  syscall
  
  li $v0, 4
  move $a0, $a3
  syscall 
  
  li $v0, 4
  la $a0, newLine
  syscall
  
  lw $ra, ($sp)
  addi $sp, $sp, 4
  
  jr $ra
  
    