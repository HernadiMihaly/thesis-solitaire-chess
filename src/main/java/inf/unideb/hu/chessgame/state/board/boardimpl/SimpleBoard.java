package inf.unideb.hu.chessgame.state.board.boardimpl;

import inf.unideb.hu.chessgame.state.board.Board;
import inf.unideb.hu.chessgame.state.pieces.Piece;
import inf.unideb.hu.chessgame.state.pieces.piecesimpl.*;

/**
 * Ez az osztály a SolitaireChess játéktábla kódbeli reprezentációja.
 */
public class SimpleBoard implements Board {
    private Tile[][] tiles;

    /**
     * A konstruktor, amely Tile-okból felépíti a kezdetben üres táblát.
     */
    public SimpleBoard() {
            tiles = new Tile[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tiles[i][j] = new Tile(i, j);
                }
            }
        }

    /**
     * Visszaad egy mezőt az x és y koordinátái alapján.
     * @param x koordináta.
     * @param y koordináta.
     * @return A mező.
     */
    @Override
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Visszaadja a tábla összes mezőjét.
     * @return A tábla mezőinek tömbje.
     */
    @Override
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Megmondja mekkora a tábla mérete (NxN esetén N-t ad vissza).
     * @return A tábla méretét (a hosszát a tömbnek).
     */
    @Override
    public int getSize() {
        return tiles.length;
    }

    /**
     * Megmondja, hogy hány figura áll a táblán éppen.
     * @return A figurák száma (int).
     */
    @Override
    public int getNumberOfPieces() {
        int count = 0;
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (isOccupied(tiles[x][y])) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Egy megadott figurát egy megadott mezőre helyez.
     * @param placeTo A megadott mező ahova helyezni akarjuk a figurát.
     * @param piece A megadott figura.
     */
    @Override
    public void placePiece(Tile placeTo, Piece piece) {
        int x= placeTo.getX();
        int y= placeTo.getY();

        if (x >= 0 && x < getSize() && y >= 0 && y < getSize()
                && !isOccupied(tiles[x][y]))
            tiles[x][y]
                    .setPiece(piece);
    }

    /**
     * Ha a megadott mezőn áll figura, akkor eltávolítja azt.
     * @param tile A megadott mező.
     */
    @Override
    public void removePiece(Tile tile) {
        int x= tile.getX();
        int y= tile.getY();

        if (isOccupied(tiles[x][y])) {
            tiles[x][y].setPiece(null);
        }
    }

    /**
     * Megvizsgálja, hogy a megadott mező foglalt-e.
     * @param tile A megadott mező.
     * @return true, ha foglalt, false, ha nem.
     */
    @Override
    public boolean isOccupied(Tile tile) {
        return tiles[tile.getX()][tile.getY()].getPiece() != null;
    }

    /**
     * Felépíti a táblaobjektumot a megadott String alapján.
     * @param boardRepresentation A megadott String.
     * @return A felépített táblaobjektum.
     */
    @Override
    public Board setBoardFromString(String boardRepresentation) {
        String[] rows = boardRepresentation.split("\n");
        for (int i = 0; i < getSize(); i++) {
            String[] columns = rows[i].split(",");
            for (int j = 0; j < getSize(); j++) {
                String pieceName = columns[j].trim();
                if (!pieceName.equals("(" + i + "," + j + ")")) {
                    Piece piece = null;
                    switch (pieceName) {
                        case "Knight":
                            piece = new Knight();
                            tiles[i][j].setPiece(piece);
                            break;
                        case "Rook":
                            piece = new Rook();
                            tiles[i][j].setPiece(piece);
                            break;
                        case "Bishop":
                            piece = new Bishop();
                            tiles[i][j].setPiece(piece);
                            break;
                        case "Pawn":
                            piece = new Pawn();
                            tiles[i][j].setPiece(piece);
                            break;
                        case "Queen":
                            piece = new Queen();
                            tiles[i][j].setPiece(piece);
                            break;
                        case "King":
                            piece = new King();
                            tiles[i][j].setPiece(piece);
                            break;
                        default:
                            break;
                    }
                    if (piece != null) {
                        tiles[i][j].setPiece(piece);
                    }
                }
            }
        }
        return this;
    }

    /**
     * Egy adott tábla String reprezentációját adja vissza.
     * @return A tábla String.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (tiles[i][j].getPiece() == null){
                    sb.append("x");
                } else {
                    Piece piece = tiles[i][j].getPiece();
                    sb.append(piece.getName());
                }
                sb.append(",");
                sb.append(" ");
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Egy adott tábla klónozására/másolására alkalmas metódus.
     * @return A klón tábla.
     */
    @Override
    public SimpleBoard clone() {
        SimpleBoard clonedBoard = new SimpleBoard();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Tile originalTile = tiles[i][j];
                Tile clonedTile = new Tile(originalTile.getX(), originalTile.getY());
                clonedTile.setTriedTiles(originalTile.getTriedTiles());
                if (originalTile.getPiece() != null) {
                    Piece originalPiece = originalTile.getPiece();
                    Piece clonedPiece = null;
                    if (originalPiece instanceof Knight) {
                        clonedPiece = new Knight();
                    } else if (originalPiece instanceof Rook) {
                        clonedPiece = new Rook();
                    } else if (originalPiece instanceof Bishop) {
                        clonedPiece = new Bishop();
                    } else if (originalPiece instanceof Pawn) {
                        clonedPiece = new Pawn();
                    } else if (originalPiece instanceof Queen) {
                        clonedPiece = new Queen();
                    } else if (originalPiece instanceof King) {
                        clonedPiece = new King();
                    }
                    clonedTile.setPiece(clonedPiece);
                }
                clonedBoard.tiles[i][j] = clonedTile;
            }
        }

        return clonedBoard;
    }
}