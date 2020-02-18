## The problem

We want you to create a command-line application that will calculate the
ranking table for a soccer league.

### Input/output

The input and output will be text. Your solution should parse the provided
`input-sample.txt` file via stdin (pipe or redirect) or by parsing a file passed
by name on the command line. Your solution should output the correct result via
stdout to the console.

The input contains results of games, one per line. See `input-sample.txt` for
details. The output should be ordered from most to least points, following the
format specified in `output-expected.txt`.

You can expect that the input will be well-formed. There is no need to add
special handling for malformed input files.

### The rules

In this league, a draw (tie) is worth 1 point and a win is worth 3 points. A
loss is worth 0 points. If two or more teams have the same number of points,
they should have the same rank and be printed in alphabetical order (as in the
tie for 3rd place in the sample data).

### Guidelines

This should be implemented in a language with which you are familiar. We would
prefer that you use Scala or Java, if you are comfortable doing so.
If none of these are comfortable, please choose a language that is both
suited to the task and productive for you.

Your solution should be able to be run (and if applicable, built) from the
command line. Please include appropriate scripts and instructions for
running your application and your tests.

If you use other libraries installed by a common package manager (for example:
npm), it is not necessary to submit the installed packages within your overall
solution.

We write automated tests and we would like you to do so as well.

We appreciate well-factored, object-oriented or functional designs.

Please document any steps necessary to run your solution and your tests.

### Platform support

This will be run in a unix-like environment, macOS. If you choose to use a
compiled language, please keep this in mind. Please use platform-agnostic
constructs where possible (line-endings and file-path-separators are two
problematic areas).
