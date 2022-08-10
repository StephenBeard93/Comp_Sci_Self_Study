.data
  newLine: .asciiz "\n"
  
.text
  addi $s0, $zero, 10
  jal increaseRegister 
  
  # print register
  li $v0, 4
  la $a0, newLine
  syscall 
  
  jal print$s0
  
  # terminate
  li $v0, 10
  syscall	
  
  
  
  increaseRegister:
    addi $sp, $sp, -8
    sw $s0, 0($sp)
    sw $ra, 4($sp)
    
    addi $s0, $s0, 30
    
    jal print$s0
    
    lw $s0, 0($sp)
    lw $ra, 4($sp)
    addi $sp, $sp, 4
    
    jr $ra
    
  print$s0:
    li $v0, 1
    add $a0, $zero, $s0
    syscall 
    
    jr $ra
   