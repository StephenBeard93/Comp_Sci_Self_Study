.data 

.text 
  addi $s0, $zero, 10
  li $s1, 4
  
  mul $t0, $s0, $s1
  
  add $a0, $zero, $t0
  li $v0, 1
  syscall 