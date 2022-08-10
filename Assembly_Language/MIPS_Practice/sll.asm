.data

.text
  addi $t0, $zero, 5
  sll $s0, $t0, 2
  
  li $v0, 1
  move $a0, $s0
  syscall 