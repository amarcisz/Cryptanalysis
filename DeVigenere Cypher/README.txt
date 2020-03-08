BreakDeVigenere.java    - Method that takes in a DeVigenere cypher and breaks the encryption in order to expose the hidden message

The Vigenere Cipher rotates each letter of the input message by an amount
dictated by the password. For example, 'A' might be treated as zero, and thus
a message containing 'HELLO' and a password of 'AB' would encode to 'HFLMO'
'H' + A -> F, 'E' + B -> F, 'L' + A -> L, etc
To reverse the vignere cipher for a given password length, we must know
the expected frequency distribution of characters in the original language.
Then, we split a message, such as HELLO by the length of the password.
In the case of length=2, the message becomes HE LL O.
Each first character of the split sections becomes the "first interleave", or
HE LL O
^  ^  ^
First interleave here is HLO
Each second character becomes the second interleave, which is "EL" here.
We can crack each interleave by decrypting it with each of the possible
pass letters, from A-Z. Then, we look at the frequency distribution of the
resulting plaintext. We choose the distribution that is closest to the target
language. This example code minimizes the square differences between
frequencies in the decoding of each interleave and english frequencies.
The previos process can be called "deceasar", since it reverses what is known
as a "Caesar Cipher" for each interleave. Finally, we recombine the decrypted
interleaves into the original text.
