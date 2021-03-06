package piece;

import board.Board;
import board.BoardUtils;
import move.AttackMove;
import move.Move;
import move.NormalMove;

import java.util.ArrayList;
import java.util.List;

public class QueenCheckerPiece extends CheckerPiece{

    final static private CheckerPieceType QUEEN_CHECKER_PIECE = CheckerPieceType.QUEEN_CHECKER_PIECE_TYPE;
    final int [] LEGAL_MOVE_OFFSET = new int [] {7, 9};

    public QueenCheckerPiece(int currentCoordinate, Alliance alliance, Board board){
        super(QUEEN_CHECKER_PIECE, currentCoordinate, alliance, board);
        legalMoves = new ArrayList<>();
    }

    /**
     *
     * @return
     *
     * Calculating not take moves
     */
    @Override
    public ArrayList<Move> calculateNotTakeMoves() {
        ArrayList<Move> result = new ArrayList<>();

        for(int offset : LEGAL_MOVE_OFFSET){
            offset *= alliance.getDirection();
            if(!((BoardUtils.FIRST_COLUMN[currentCoordinate] && offset == 7)
                    || (BoardUtils.FIRST_COLUMN[currentCoordinate] && offset == -9)
                    || (BoardUtils.EIGHTH_COLUMN[currentCoordinate] && offset == 9)
                    || (BoardUtils.EIGHTH_COLUMN[currentCoordinate] && offset == -7))) {


                int destinationCoordinate = currentCoordinate + offset ;

                if (!BoardUtils.inTheLimit(destinationCoordinate)) {
                    continue;
                }

                if (board.getTile(destinationCoordinate).isTileEmpty()) {
                    result.add(new NormalMove(currentCoordinate, destinationCoordinate, this, alliance)); // TODO Move class will be done later
                }
            }
        }

        legalMoves = result;
        return result;
    }

    @Override
    public ArrayList<Move> calculateTakeMoves(){

        ArrayList<Move> takeMoves = new ArrayList<>();

        for(int offset : LEGAL_MOVE_OFFSET){

            offset *= alliance.getDirection();

            int neighbourTile = currentCoordinate + offset;
            int destinationTile = currentCoordinate + 2 * offset;

            if(!(BoardUtils.inTheLimit(neighbourTile) && BoardUtils.inTheLimit(destinationTile))){
                continue;
            }

            if(board.getTile(neighbourTile).getPieceOnTile() != null
                    && board.getTile(neighbourTile).getPieceOnTile().getAlliance() != alliance
                    && board.getTile(destinationTile).isTileEmpty()
                    && !((BoardUtils.FIRST_COLUMN[neighbourTile] && offset == 7)
                    || (BoardUtils.FIRST_COLUMN[neighbourTile] && offset == -9)
                    || (BoardUtils.EIGHTH_COLUMN[neighbourTile] && offset == 9)
                    || (BoardUtils.EIGHTH_COLUMN[neighbourTile] && offset == -7))){

                takeMoves.add(new AttackMove(currentCoordinate
                        , destinationTile
                        , this
                        ,neighbourTile));
            }

        }
        legalMoves = takeMoves;
        return legalMoves;

    }
    @Override
    public String toString(){
        return (alliance.getAlliance() == Alliance.WHITE ? "Q" : "q");
    }


    /**
     *  @param move
     *  @param board
     *  @return
     *
     *  Executes the move on the board and the returns new board
     */

    @Override
    public Board executeMove(Move move, Board board){
        boolean isThereAnyTakeMove = false;
        if(move instanceof AttackMove){
            AttackMove attackMove = (AttackMove) move;
            int destinationCoordinate = move.getDestinationCoordinate();
            Alliance alliance = board.getTile(move.getCurrentCoordinate()).getPieceOnTile().getAlliance();
            board.getTile(move.getDestinationCoordinate()).setPieceOnTile(new NormalCheckerPiece(destinationCoordinate, alliance, board));
            board.getTile(move.getCurrentCoordinate()).setPieceOnTile(null);
            board.setPieceOnTile(attackMove.getTakenPieceCoordinate(), null); // Terminates the board
            if(!board.getTile(destinationCoordinate).getPieceOnTile().calculateTakeMoves().isEmpty()){
                isThereAnyTakeMove = true;
            }

            if(isThereAnyTakeMove){
                board.setAlliance(board.alliance().getAlliance());
            }
            return board;
        }

        else if (move instanceof NormalMove){
            int destinationCoordinate = move.getDestinationCoordinate();
            Alliance alliance = board.getTile(move.getCurrentCoordinate()).getPieceOnTile().getAlliance();
            board.getTile(move.getDestinationCoordinate()).setPieceOnTile(new NormalCheckerPiece(destinationCoordinate, alliance, board));
            board.getTile(move.getCurrentCoordinate()).setPieceOnTile(null);
        }



        else{
            board.setAlliance(board.alliance().getOpposite());
        }

        return board;

    }

}
