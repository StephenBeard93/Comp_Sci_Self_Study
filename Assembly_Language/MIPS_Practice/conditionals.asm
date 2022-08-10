.data
  message: .asciiz "Numbers are equal"
  message2: .asciiz  "Numbers are not equal"
.text
  main:
    addi $t0, $zero, 10
    addi $t1, $zero, 10
  
    beq $t0, $t1, numbersEqual
    
    li $v0, 10
    syscall 
    
   numbersEqual:
     li $v0, 4
     la $a0, message
     syscall 
     
     li $v0, 10
     syscall 
  
  numNotEqual: 
    li $v0, 4 
    la $a0, message2
    
    li $v0, 10
    syscall 
    
