.data
  prompt: .asciiz "Enter your age: "
  message: .asciiz "\nYour age is "
.text
  li $v0, 4
  la $a0, prompt 
  syscall
  
  # get user input 
  li $v0, 5
  syscall 
  
  # save user input temporarily 
  move $t0, $v0
  
  li $v0, 4
  la $a0, message
  syscall 
  
  li $v0, 1
  move $a0, $t0
  syscall 
  
  