.text
	.align 4
.globl  test
test:
test_bb2:
test_bb3:
	movl	$0, %EAX
	movl	$1, %EAX
	movl	$0, %EAX
	movl	%EAX, %EDI
	cmpl	%EDI, %ESI
	jg	test_bb7
test_bb4:
	movl	$1, %EAX
	subl	%EAX, %EDI
test_bb7:
	movl	$0, %EAX
	cmpl	%EAX, %ESI
	jne	test_bb1
	movl	$2, %EAX
	movl	%ESI, %EDI
	subl	%EAX, %EDI
test_bb1:
	ret
