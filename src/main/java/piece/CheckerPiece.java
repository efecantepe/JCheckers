package piece;

import board.Board;
import move.Move;

import java.util.ArrayList;
import java.util.List;

/**
 *   Checker Piece class is created for representing the common attributes of the piece types
 */
public abstract class CheckerPiece {

    protected CheckerPieceType checkerPieceType;
    protected int currentCoordinate;
    protected final Alliance alliance;
    protected final Board board;
    protected boolean isThereAnyTakeMove;
    protected ArrayList<Move> legalMoves;

    protected CheckerPiece(CheckerPieceType checkerPieceType, int currentCoordinate, Alliance alliance, Board board){
        this.checkerPieceType = checkerPieceType;
        this.currentCoordinate = currentCoordinate;
        this.alliance = alliance;
        this.board = board;
        this.isThereAnyTakeMove = false;
    }

    public CheckerPieceType getType(){
        return this.checkerPieceType;
    }

    public abstract ArrayList<Move> calculateNotTakeMoves();
    public abstract ArrayList<Move> calculateTakeMoves();

    @Override
    public String toString(){
        return "The piece type is: " + checkerPieceType + "\n ";
    }

    protected void setIsThereAnyTakeMove(boolean a){
        isThereAnyTakeMove = a;
    }

    public int getCurrentCoordinate(){
        return this.currentCoordinate;
    }

    public Alliance getAlliance(){
        return alliance;
    }

    public ArrayList<Move> getLegalMoves(){
        return legalMoves;
    }

    public abstract Board executeMove( Move move , Board board);


}

