.data
  myMessage: .byte 'm'

.text
  li $v0, 4
  la $a0, myMessage
  syscall 