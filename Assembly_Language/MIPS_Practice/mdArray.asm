.data
  mdArray: .word 2, 5
           .word 3, 7
  size:    .word 2
  .eqv DATA_SIZE 4 
.text
  main:
    la $a0, mdArray
    lw $a1, size 
    jal sumDiagonal 
    move $a0, $v0        # sum of diagnol in $a0 for printing
    li $v0, 1
    syscall 
    
    li $v0, 10
    syscall 
    
  sumDiagonal:
    li $v0, 0            # set sum to zero 
    li $t0, 0            # set index to zero
    
    sumLoop:
      mul $t1, $t0, $a1   # row number * number of columns 
      add $t1, $t1, $t0   # add col index position 
      mul $t1, $t1, DATA_SIZE
      add $t1, $t1, $a0
      
      lw $t2, ($t1)
      add $v0, $v0, $t2  
      
      addi $t0, $t0, 1
      blt $t0, $a1, sumLoop 
      
      jr $ra