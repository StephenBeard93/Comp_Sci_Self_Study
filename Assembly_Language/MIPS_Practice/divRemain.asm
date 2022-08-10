.data

.text
  addi $t0, $zero, 30
  addi $t1, $zero, 8
  
  div $t0, $t1
  mfhi $s0
  
  li $v0, 1 
  move $a0, $s0
  syscall 