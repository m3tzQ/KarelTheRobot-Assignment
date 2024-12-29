import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
    private int Rows = 0,Columns = 0;
    private int movesCounter = 0;
    private int beepersCounter = 0;
    private int direction_Idx = 0;
    char[] Direction = {'R', 'U', 'L', 'D'};
    private int PosX = 1;
    private int PosY = 1;
    private boolean divideSwitch = false;
    private void Put(boolean putBeepers){ if(putBeepers && noBeepersPresent()) {putBeeper(); beepersCounter++;}}
    private void IncrementAndPrint(){ movesCounter++; System.out.println("Current Number of Moves : " + movesCounter);}
    public void turnFaceDirectionTo(char new_direction){
        while (Direction[direction_Idx] != new_direction){
            direction_Idx = (direction_Idx + 1) % 4;
            turnLeft();
        }
    }
    private void moveTo(char direction){
        turnFaceDirectionTo(direction);
        move();
        switch (direction){
            case 'R': PosX++; break;
            case 'L': PosX--; break;
            case 'U': PosY++; break;
            case 'D': PosY--; break;
        }
        IncrementAndPrint();
    }
    private void moveTo(int destinationX,int destinationY,boolean putBeepers){
        while (PosX != destinationX){
            Put(putBeepers);
            if (destinationX < PosX) moveTo('L');
            else moveTo('R');
        }
        Put(putBeepers);

        while (PosY != destinationY){
            Put(putBeepers);
            if (destinationY < PosY) moveTo('D');
            else moveTo('U');
        }
        Put(putBeepers);
    }
    private void setDiminutions(){
        turnFaceDirectionTo('R');
        while (frontIsClear()){
            moveTo('R');
        }
        turnFaceDirectionTo('U');
        while (frontIsClear()){
            moveTo('U');
        }
        Columns = PosX;
        Rows = PosY;
    }
    private void _init(){
        setBeepersInBag(10000);
        PosX = 1;
        PosY = 1;
        movesCounter = 0;
        beepersCounter = 0;
        direction_Idx = 0;
        setDiminutions();
    }
    private void divideVertically(int extra, int jump){
        boolean flag = true;
        int moveMark = 0;
        if(!divideSwitch){
            for(int i=0;i<extra;i++){
                moveMark = 1;
                if(flag){
                    moveTo(Columns - i, Rows,false);
                    moveTo(Columns - i, 1,true);
                }
                else{
                    moveTo(Columns - i, 1,false);
                    moveTo(Columns - i , Rows,true);
                }
                flag = !flag;
            }
            while (PosX - jump - moveMark > 0) {
                if (flag) {
                    moveTo(PosX - jump - moveMark, Rows, false);
                    moveTo(PosX, 1, true);
                } else{
                    moveTo(PosX - jump - moveMark, 1, false);
                    moveTo(PosX, Rows, true);
                }
                moveMark = 1;
                flag = !flag;
            }
        }
        else{
            for(int i=0;i<extra;i++){
                moveMark = 1;
                if(flag){
                    moveTo(Columns , Rows - i,false);
                    moveTo(1, Rows - i,true);
                }
                else{
                    moveTo(1, Rows - i,false);
                    moveTo(Columns, Rows - i,true);
                }
                flag = !flag;
            }
            while (PosY - jump - moveMark> 0) {
                if (flag) {
                    moveTo(Columns, PosY - jump - moveMark, false);
                    moveTo(1, PosY, true);
                } else {
                    moveTo(1, PosY - jump - moveMark, false);
                    moveTo(Columns, PosY, true);
                }
                moveMark = 1;
                flag = !flag;
            }
        }
    }
    private void divideDiagonally(){
        int Current = Rows;
        while(Current != 0){
            moveTo(Current, Current,false);
            Put(true);
            Current--;
        }
        int CurrentX = 1;
        int CurrentY = Columns;
        while(CurrentY > 0){
            moveTo(CurrentX,CurrentY,false);
            Put(true);
            CurrentX++;
            CurrentY--;
        }
    }
    private void dividePlusSpecial(boolean oddside) {
        if(!oddside){
            int nextX = Columns;
            int nextY = Rows / 2 + 1;
            moveTo(nextX, nextY, false);
            nextX = 1;
            moveTo(nextX,nextY,true);
            nextX = Columns / 2;
            nextY = Rows;
            moveTo(nextX, nextY, false);
            int breaker = (int) Math.ceil( (double) (Rows / 2) / 2);
            nextY = Rows - (breaker - 1);
            moveTo(nextX, nextY, true);
            if((Rows / 2) % 2 != 0){
                nextX += 1;
                moveTo(nextX, nextY, true);
            }
            else{
                nextX += 1;
                nextY -= 1;
                moveTo(nextX, nextY, false);
            }
            nextY -= breaker * 2;
            moveTo(nextX, nextY, true);
            if((Rows / 2) % 2 != 0){
                nextX -= 1;
                moveTo(nextX, nextY, true);
            }
            else{
                nextX -= 1;
                nextY -= 1;
                moveTo(nextX, nextY, false);
            }
            nextY = 1;
            moveTo(nextX, nextY, true);
        }
        else{
            int nextX = Columns / 2 + 1;
            int nextY = Rows;
            moveTo(nextX, nextY, false);
            nextY = 1;
            moveTo(nextX,nextY,true);
            nextX = Columns;
            nextY = Rows / 2 + 1;
            moveTo(nextX, nextY, false);
            int breaker = (int) Math.ceil( (double) (Columns / 2) / 2);
            nextX = Columns - (breaker - 1);
            moveTo(nextX, nextY, true);
            if((Columns / 2) % 2 != 0){
                nextY -= 1;
                moveTo(nextX, nextY, true);
            }
            else{
                nextX -= 1;
                nextY -= 1;
                moveTo(nextX, nextY, false);
            }
            nextX -= breaker * 2;
            moveTo(nextX, nextY, true);
            if((Columns / 2) % 2 != 0){
                nextY += 1;
                moveTo(nextX, nextY, true);
            }
            else{
                nextX -= 1;
                nextY += 1;
                moveTo(nextX, nextY, false);
            }
            nextX = 1;
            moveTo(nextX, nextY, true);
        }
    }
    private void dividePlus() {
        int nextX = Columns / 2 + 1;
        int nextY = Rows;
        moveTo(nextX, nextY, false);
        nextY = 1;
        moveTo(nextX,nextY,true);
        if(Columns % 2 == 0){
            nextX = Columns / 2;
            moveTo(nextX, nextY, false);
            nextY = Rows;
            moveTo(nextX,nextY,true);
        }
        nextX = Columns;
        nextY = Rows / 2 + 1;
        moveTo(nextX, nextY, false);
        nextX = 1;
        moveTo(nextX,nextY,true);
        if(Rows % 2 == 0){
            nextY = Rows / 2 ;
            moveTo(nextX, nextY, false);
            nextX = Columns;
            moveTo(nextX,nextY,true);
        }
    }
    private void divideSpecial2xN(){
        int beepersCount = 0;
        boolean flag = true;
        for(int i = 0; i < Columns; i++){
            if(beepersCount < 4){
                moveTo(Columns - i, flag ? Rows : 1,false);
                Put(true);
                beepersCount++;
            }
            else{
                moveTo(Columns - i, flag ? 1 : Rows,false);
                moveTo(Columns - i, flag ? Rows : 1,true);
            }
            flag = !flag;
        }
    }
    private void divideSpecial1xN(){
        boolean flag = false;
        while(PosX - 1 >= 1){
            moveTo('L');
            flag = !flag;
            if(flag) Put(true);
        }
    }
    public void runner(){
        if(Rows + Columns <= 3) return;
        if(Columns == Rows && Rows % 2 == 0 && Rows >= 3) {divideDiagonally(); return;}
        if(Columns == Rows && Rows % 2 != 0 && Rows >= 3) {dividePlus(); return;}
        int minVal = Math.min(Rows,Columns);
        int maxVal = Math.max(Rows,Columns);
        divideSwitch = (Columns == minVal);
        if(minVal == 2 && maxVal <= 6) {divideSpecial2xN(); return;}
        if(minVal == 1 && maxVal <= 6) {divideSpecial1xN(); return;}
        if(maxVal <= 6) {dividePlus(); return;}
        int plusCounter;
        if(Rows % 2 == 0 && Columns % 2 == 0) plusCounter = Rows * 2 + Columns * 2 - 4;
        else if(Rows % 2 != 0 && Columns % 2 == 0){
            if((Rows / 2) % 2 == 0) plusCounter = Rows + Columns - 1;
            else plusCounter = Rows + Columns + 1;
        }
        else if(Rows % 2 == 0 && Columns % 2 != 0){
            if((Columns / 2) % 2 == 0) plusCounter = Rows + Columns - 1;
            else plusCounter = Rows + Columns + 1;
        }
        else plusCounter = Rows  + Columns - 1;

        int verticalCounter, extraLines, jumpValue;
        int Val = maxVal / 4, reminder = maxVal % 4;
        if(reminder != 3) {Val--; reminder += 4;}
        jumpValue = Val;
        extraLines = reminder - 3;
        verticalCounter = reminder * minVal;

        if(verticalCounter <= plusCounter) divideVertically(extraLines,jumpValue);
        else{
            if(Rows % 2 != 0 && Columns % 2 == 0 && Rows > 3) dividePlusSpecial(false);
            else if(Rows % 2 == 0 && Columns % 2 != 0 && Columns > 3) dividePlusSpecial(true);
            else dividePlus();
        }
    }
    public void run(){
        _init();
        runner();
        moveTo(1,1,false); // return to origin
        System.out.println("Total Number of beepers : #" + beepersCounter);
    }
}
