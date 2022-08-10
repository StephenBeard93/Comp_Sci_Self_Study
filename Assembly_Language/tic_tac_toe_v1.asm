.data
  positions: .asciiz "123456789"
  dashes:    .asciiz "\n-----\n"
  pipe:      .byte '|'
  x:         .byte 'X'
  o:         .byte 'O'
  msg1:      .asciiz "Let the battle of wits begin - you are player X \n"
  msg2:      .asciiz "\nChoose a number and press enter: \n"
  msg3:      .asciiz "\nYou are the WINNER!*!*!"
  msg4:      .asciiz "\nPlayer "
  msg5:      .asciiz " turn"
  msg6:      .asciiz "\nIt's a draw - GAME OVER"

.text
  main: 
    # reserve s0 as counter for which players go it is, if there is a remainder after dividing by 2 then insert player 'X' symbol
    li $s0, 1
    
    # reserve s1 has the hasWon check register, if 3 end game, else return to start of gamePlay loop
    addi, $s1, $zero, 0
    
    li $v0, 4
    la $a0, msg1
    syscall
    
    # print board
    la $t0, positions                # set t0 to the address of positions
    addi $a1, $zero, 9               # set a1 to the length of positions
    addi $a2, $zero, 0		      # set a2 as counter to zero
    jal printBoard
    
    gamePlay:
      # get user input
      jal getUserInput
      move $a1, $v0                    # set a1 to be the user input value returned from procedure
      subi $a1, $a1, 1                 # set user input to be indexed from zero
    
      # update board
      la $t0, positions
      jal updateBoard
      
      # check if there is a winner or the game is over
      jal hasWon
      move $s1, $v0
    
      # print board
      la $t0, positions                # set t0 to the address of positions
      addi $a1, $zero, 9               # set a1 to the length of positions
      addi $a2, $zero, 0		# set a2 as counter to zero
      jal printBoard
    
      # Update players go
      addi $s0, $s0, 1
      
      # check if draw 
      beq $s0, 10, draw
      
      # if no winner, re-enter gamePlay loop
      bne $s1, 3, gamePlay
      
      # if winner print victory message
      bne $s1, 3, endProgram
  
      li $v0, 4
      la $a0, msg3
      syscall 
      
      # terminate program
      endProgram:
        li $v0, 10
        syscall 
      
      draw:
        li $v0, 4
        la $a0, msg6
        syscall 
        
        li $v0, 10
        syscall      
    
  printBoard:
    #store return address
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    printPosition:
      # print selected byte from positions
      li $v0, 11
      lb $a0, ($t0)
      syscall 
    
      # print new line if counter divisible by 3 
      li $t1, 3
      addi $t2, $a2, 1                  # increment counter by 1 so index starts on 1 rather than 0
      div $t2, $t1                      # divide number of positions printed by 3
      mfhi $t1                          # set t1 to remainder
    
      beqz $t1, printDashesBranch       # if remainder equals zero, skip printPipe and go to printDashesBranch
      jal printPipe
    
    printDashesBranch:
      bnez  $t1, increment              # if a pipe was printed, skip printDahses and go to increment
      jal printDashes
     
    increment:   
      # increment address and counter
      addi $t0, $t0, 1                  # increase position address by 1
      addi $a2, $a2, 1                  # increase counter a2 by 1
    
      blt $a2, $a1, printPosition       # loop back to printPosition until 9 iterations have been completed
    
      lw $ra, ($sp)
      addi $sp, $sp, 4
      jr $ra
    
  printPipe:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    # print pipe symbol in between
    li $v0, 11
    lb $a0, pipe
    syscall
    
    lw $ra, ($sp)
    addi $sp, $sp, 4
    
    jr $ra
    
  printDashes:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    beq $a2, 8, pdDone                   # don't print dashes at the bottom of the board
   
    li $v0, 4
    la $a0, dashes
    syscall
    
    pdDone:
      lw $ra, ($sp)
      addi $sp, $sp, 4
    
      jr $ra
      
  getUserInput:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    li $v0, 4
    la $a0, msg4
    syscall 
    
    jal playerSymbol
    lb $a0, ($v0)
    li $v0, 11
    syscall
    
    li $v0, 4
    la $a0, msg5
    syscall
    
    li $v0, 4
    la $a0, msg2
    syscall 
    
    li $v0, 5
    syscall 
    
    lw $ra, ($sp)
    addi $sp, $sp, 4
    
    jr $ra
    
  updateBoard:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    add $t0, $t0, $a1                     # t0 set to address of byte in positions to update
    jal playerSymbol
    lb $t4, ($v0)
    sb $t4, ($t0)                         # player symbol stored in positions array 
    
    lw $ra, ($sp)
    addi $sp, $sp, 4
    
    jr $ra
    
  playerSymbol:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    li $t3, 2
    div $s0, $t3
    mfhi $t3
    
    beqz $t3, playerO
    la $v0, x
    
    playerO:
      bnez $t3, psDone
      la $v0, o
      
      psDone:
        lw $ra, ($sp)
        addi $sp, $sp, 4
    
        jr $ra
        
  hasWon:
    subi $sp, $sp, 4
    sw $ra, ($sp)
    
    jal playerSymbol
    lb $t4, ($v0)                # set t4 to be the most recent players symbol 
    addi $t3, $zero, 0           # set t3 to be counter register, initialise to zero
    
    hasWonCheck_h:                   # hasWon check loop
      la $t0, positions
      mul $t8, $t3, 3
      
      add $t0, $t0, $t8            # add 3 bytes to positions address to access start of next row
      jal checkHorizontal
      addi $t3, $t3, 1
      beq  $v0, 3, hasWonDone      # jump to hasWonDone if it returns 3
      bne $t3, 3, hasWonCheck_h
      addi $t3, $zero, 0           # set t3 to be counter register, initialise to zero
      
    hasWonCheck_v:
      la $t0, positions
      mul $t8, $t3, 1
      
      add $t0, $t0, $t8
      jal checkVertical
      addi $t3, $t3, 1
      beq  $v0, 3, hasWonDone      # jump to hasWonDone if it returns 3
      bne $t3, 3, hasWonCheck_v
      
    hasWonCheck_rld:
      la $t0, positions
      jal checkDiagonal_rld
      beq  $v0, 3, hasWonDone      # jump to hasWonDone if it returns 3
      
    hasWonCheck_lrd:
      la $t0, positions
      jal checkDiagonal_lrd
      beq  $v0, 3, hasWonDone      # jump to hasWonDone if it returns 3
      
      hasWonDone:
        lw $ra, ($sp)
        addi $sp, $sp, 4
     
        jr $ra
        
  ## if checkHorisontal returns a 3 then there has been a match ##
  checkHorizontal:
    subi $sp, $sp, 8
    sw $ra, ($sp)
    sw $t0, 4($sp)
    addi $t6, $zero, 0           # set t6 to be counter for symbol matches
    addi $t7, $zero, 0           # set t7 to be the counter of loop iterations
    
    chLoop:
    beq $t7, 3, chDone           # skip to return if 3 loops have been completed
    
    lb $t5, ($t0)                # set t5 to first bite of positions array 
    bne  $t4, $t5, chDone        # skip to return if symbol is not a match
    
    addi $t6, $t6, 1             # add one to counter if symbols match
    addi $t0, $t0, 1             # increment one through postions array
    
    j  chLoop
      
    chDone:
      move $v0, $t6
      lw $ra, ($sp)
      lw $t0, 4($sp)
      addi $sp, $sp, 8
     
      jr $ra
  
  checkVertical:
    subi $sp, $sp, 8
    sw $ra, ($sp)
    sw $t0, 4($sp)
    addi $t6, $zero, 0           # set t6 to be counter for symbol matches
    addi $t7, $zero, 0           # set t7 to be the counter of loop iterations
    
    cvLoop:
    beq $t7, 3, cvDone           # skip to return if 3 loops have been completed
    
    lb $t5, ($t0)                # set t5 to first bite of positions array 
    bne  $t4, $t5, cvDone        # skip to return if symbol is not a match
    
    addi $t6, $t6, 1             # add one to counter if symbols match
    addi $t0, $t0, 3             # increment three through postions array
    
    j  cvLoop
      
    cvDone:
      move $v0, $t6
      lw $ra, ($sp)
      lw $t0, 4($sp)
      addi $sp, $sp, 8
     
      jr $ra
  
  # check diagonal right to left \
  checkDiagonal_rld:
    subi $sp, $sp, 8
    sw $ra, ($sp)
    sw $t0, 4($sp)
    addi $t6, $zero, 0           # set t6 to be counter for symbol matches
    addi $t7, $zero, 0           # set t7 to be the counter of loop iterations
    
    rldLoop:
    beq $t7, 3, rldDone           # skip to return if 3 loops have been completed
    
    lb $t5, ($t0)                # set t5 to first bite of positions array 
    bne  $t4, $t5, rldDone        # skip to return if symbol is not a match
    
    addi $t6, $t6, 1             # add one to counter if symbols match
    addi $t0, $t0, 4             # increment four through postions array
    
    j  rldLoop
      
    rldDone:
      move $v0, $t6
      lw $ra, ($sp)
      lw $t0, 4($sp)
      addi $sp, $sp, 8
     
      jr $ra
  
  # check diasonal left to right /
  checkDiagonal_lrd:
    subi $sp, $sp, 8
    sw $ra, ($sp)
    sw $t0, 4($sp)
    addi $t6, $zero, 0           # set t6 to be counter for symbol matches
    addi $t7, $zero, 0           # set t7 to be the counter of loop iterations
    addi $t0, $t0, 2             # increment starting byte by two in positions array 
    lrdLoop:
    beq $t7, 3, lrdDone           # skip to return if 3 loops have been completed
    
    lb $t5, ($t0)                # set t5 to first bite of positions array 
    bne  $t4, $t5, lrdDone        # skip to return if symbol is not a match
    
    addi $t6, $t6, 1             # add one to counter if symbols match
    addi $t0, $t0, 2             # increment four through postions array
    
    j  lrdLoop
      
    lrdDone:
      move $v0, $t6
      lw $ra, ($sp)
      lw $t0, 4($sp)
      addi $sp, $sp, 8
     
      jr $ra
  