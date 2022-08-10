.data
  message: .asciiz "Hellllllllloooooo"
.text
  main:
    addi $t0, $zero, 200
    addi $t1, $zero, 200
  
    bgt $t0, $t1, printMessage
    
    li $v0, 10
    syscall 
    
    
  printMessage:
    li $v0, 4
    la $a0, message
    syscall