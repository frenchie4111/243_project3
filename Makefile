build: src/Water.java src/Puzzle.java src/Clock.java src/Solver.java
	javac -d bin -sourcepath src/ src/Water.java src/Clock.java

build-test-water: src/TestWater.java
	javac -d bin -sourcepath src/ src/TestWater.java

build-test-clock: src/TestClock.java
	javac -d bin -sourcepath src/ src/TestClock.java

test: 
	make build
	make build-test-water
	make build-test-clock
	echo 'Testing water'
	java -cp bin/ TestWater
	echo 'Testing clock'
	java -cp bin/ TestClock

try: 
	try grd-243 project2-1 src/Clock.java src/Water.java src/Puzzle.java src/Solver.java src/design.txt

push: 
	git add .
	git commit
	git push

clean:
	rm -rf *.class
	rm -rf bin/*.class
