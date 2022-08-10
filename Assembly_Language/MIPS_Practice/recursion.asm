.data
  promtMessage: .asciiz "Enter a number to find it's factorial "
  resultMessage: .asciiz "\n The factorial is "
  myNumber: .word 0
  answer: .word 0

.text
  .globl main
  main: 
    addi $t1, $zero, 0
    sw $t1, myNumber
    
    li $v0, 1
    la $a0, myNumber
    syscall
    
    # promt user
    li $v0, 4
    la $a0, promtMessage
    syscall 
    
    li $v0, 5
    syscall
    
    sw $v0, myNumber
    
    #Bug Test-----
    li $v0, 1
    la $a0, myNumber
    syscall
    
    #call the factorial function
    lw $a0, myNumber
    jal findFactorial
    sw $v0, answer
    
    # print result message and answer
    li $v0, 4
    la $a0, resultMessage
    syscall 
    
    li $v0, 1
    la $a0, answer
    syscall 
    
    #exit program
    li $v0, 10
    syscall 
  #------------------------------------------
  .globl findFactorial  
  findFactorial:
    #save variable register to stack
    subu $sp, $sp, 8
    sw $ra, ($sp)
    sw $s0, 4($sp)
    
    # basecase
    li $v0, 1
    beq $a0, 0, factorialDone
    
    # factorial (n-1)
    move $s0, $a0
    sub $a0, $a0, 1
    jal findFactorial
    
    # magic
    mul $v0, $v0, $s0
    
    
  factorialDone:
    lw $ra, ($sp)
    lw $s0, 4($sp)
    addu $sp, $sp, 8
    jr $ra
