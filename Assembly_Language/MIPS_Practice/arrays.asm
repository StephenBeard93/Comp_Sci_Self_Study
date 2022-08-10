.data
  myArray: .space 12
.text
  addi $s0, $zero, 5
  addi $s1, $zero, 10
  addi $s2, $zero, 15
  
  # zero index register
  addi $t0, $zero, 0
  
  # save to stack
  sw $s0, myArray($t0)
    addi $t0, $t0, 4
  sw $s1, myArray($t0)
    addi $t0, $t0, 4
  sw $s2, myArray($t0)
    addi $t0, $t0, 4
    
  addi $t0, $zero, 8
  lw $t6, myArray($t0)
  
  li $v0, 1
  move $a0, $t6
  syscall 