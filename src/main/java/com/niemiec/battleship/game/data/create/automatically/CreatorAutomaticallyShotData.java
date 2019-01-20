package com.niemiec.battleship.game.data.create.automatically;

import com.niemiec.battleship.game.data.check.CheckData;
import com.niemiec.battleship.game.data.check.CheckShotData;
import com.niemiec.battleship.game.objects.Board;
import com.niemiec.battleship.game.objects.Coordinates;
import com.niemiec.battleship.game.objects.Player;
import com.niemiec.battleship.game.objects.PlayerImpl;

@SuppressWarnings("serial")
public class CreatorAutomaticallyShotData extends CreatorAutomaticallyData {
	
	private PlayerImpl players[];
	
	public CreatorAutomaticallyShotData(PlayerImpl[] players) {
		this.players = players;
	}

	public Coordinates downloadShotFromVirtualPlayer(int activePlayer) {
//		CheckShotData.setVariablesToCheckShotData(players[activePlayer].getOpponentBoard());
		CheckData.setVariablesToCheckData(players[activePlayer].getOpponentBoard());
		if (players[activePlayer].isOnHit()) {
			return nextMoveVirtualPlayer(activePlayer);
		}
		return firstMoveVirtualPlayer(activePlayer);
	}

	private Coordinates nextMoveVirtualPlayer(int activePlayer) {
		int directionOnHit = players[activePlayer].getDirectionOnHit();
		if (directionOnHit == directionNotSelected) {
			return hitOfSecondMastByVirtualPlayer(activePlayer);
		} else {
			return hitOfTheOthersMastsByVirtualPlayer(activePlayer);
		}
	}

	private Coordinates hitOfTheOthersMastsByVirtualPlayer(int activePlayer) {
		PlayerImpl player = players[activePlayer];
		Coordinates coordinatesHit = player.getCoordinatesOnHit();
		Coordinates nextCoordinates = null;
		int side;
		while (true) {
			side = random.nextInt(2);
			if (player.getDirectionOnHit() == directionX) {
				nextCoordinates = selectedCoordinatesOtherMastsDirectionX(side, coordinatesHit, activePlayer);
			} else if (player.getDirectionOnHit() == directionY) {
				nextCoordinates = selectedCoordinatesOtherMastsDirectionY(side, coordinatesHit, activePlayer);
			}
			
			if (nextCoordinates != null)
				return nextCoordinates;
		}
	}

	private Coordinates selectedCoordinatesOtherMastsDirectionY(int side, Coordinates coordinatesHit,	int activePlayer) {
		PlayerImpl player = players[activePlayer];
		int opponentPlayer = getIndexOpponentPlayer(activePlayer);
		int currentNumberOfHitMasts = players[opponentPlayer].getCollectionShips().getShip(coordinatesHit).getCurrentNumberOfHitMasts();
		if (side == rightAndDown 
				&& new Coordinates(coordinatesHit, 1, 0).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 1, 0)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, 1, 0);
		} else if (side == rightAndDown
				&& new Coordinates(coordinatesHit, currentNumberOfHitMasts, 0).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, currentNumberOfHitMasts - 1, 0)) == Board.BOX_HIT
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, currentNumberOfHitMasts, 0)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, currentNumberOfHitMasts, 0);
		}
		
		if (side == leftAndTop 
				&& new Coordinates(coordinatesHit, -1, 0).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, -1, 0)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, -1, 0);
		} else if (side == leftAndTop
				&& new Coordinates(coordinatesHit, -currentNumberOfHitMasts, 0).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, -currentNumberOfHitMasts + 1, 0)) == Board.BOX_HIT
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, -currentNumberOfHitMasts, 0)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, -currentNumberOfHitMasts, 0);
		}
		return null;
	}

	private Coordinates selectedCoordinatesOtherMastsDirectionX(int side, Coordinates coordinatesHit, int activePlayer) {
		PlayerImpl player = players[activePlayer];
		int opponentPlayer = getIndexOpponentPlayer(activePlayer);
		int currentNumberOfHitMasts = players[opponentPlayer].getCollectionShips().getShip(coordinatesHit).getCurrentNumberOfHitMasts();
		if (side == rightAndDown 
				&& new Coordinates(coordinatesHit, 0, 1).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, 1)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, 0, 1);
		} else if (side == rightAndDown
				&& new Coordinates(coordinatesHit, 0, currentNumberOfHitMasts).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, currentNumberOfHitMasts - 1)) == Board.BOX_HIT
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, currentNumberOfHitMasts)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, 0, currentNumberOfHitMasts);
		}
		
		if (side == leftAndTop 
				&& new Coordinates(coordinatesHit, 0, -1).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, -1)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, 0, -1);
		} else if (side == leftAndTop
				&& new Coordinates(coordinatesHit, 0, -currentNumberOfHitMasts).checkIfWithinThePlayingField()
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, -currentNumberOfHitMasts + 1)) == Board.BOX_HIT
				&& player.getOpponentBoard().getBox(new Coordinates(coordinatesHit, 0, -currentNumberOfHitMasts)) == Board.BOX_EMPTY) {
			return new Coordinates(coordinatesHit, 0, -currentNumberOfHitMasts);
		}
		return null;
	}

	private Coordinates hitOfSecondMastByVirtualPlayer(int activePlayer) {
		int opponentPlayer = getIndexOpponentPlayer(activePlayer);
		PlayerImpl player = players[activePlayer];
		Coordinates coordinatesHit = player.getCoordinatesOnHit();
		Coordinates nextCoordinates = null;
		int direction;
		int side;
		
		while (true) {
			direction = random.nextInt(2) + 1;
			side = random.nextInt(2);
			if (direction == directionX) {
				nextCoordinates = selectedCoordinatesSecondMastDirectionX(side, coordinatesHit);
			} else if (direction == directionY) {
				nextCoordinates = selectedCoordinatesSecondMastDirectionY(side, coordinatesHit);
			}
			
			if (nextCoordinates.checkIfWithinThePlayingField() && getBoxFromOpponentBoard(nextCoordinates, activePlayer) == Board.BOX_EMPTY) {
				break;
			}
		}
		
		if (getBoxFromPlayerBoard(nextCoordinates, opponentPlayer) == Board.BOX_SHIP) {
			player.setDirectionOnHit(direction);
		}
		return nextCoordinates;
		
	}

	private Coordinates selectedCoordinatesSecondMastDirectionY(int side, Coordinates coordinatesHit) {
		Coordinates coordinates = null;
		if (side == rightAndDown) {
			coordinates = new Coordinates(coordinatesHit, 1, 0);
		} else {
			coordinates = new Coordinates(coordinatesHit, -1, 0);
		}
		return coordinates;
	}

	private Coordinates selectedCoordinatesSecondMastDirectionX(int side, Coordinates coordinatesHit) {
		Coordinates coordinates = null;
		if (side == rightAndDown) {
			coordinates = new Coordinates(coordinatesHit, 0, 1);
		} else {
			coordinates = new Coordinates(coordinatesHit, 0, -1);
		}
		return coordinates;
	}

	private Coordinates firstMoveVirtualPlayer(int activePlayer) {
		Coordinates coordinates = null;
		while (true) {
			coordinates = randomTheFirstMast();
			int box = getBoxFromOpponentBoard(coordinates, activePlayer);
			if (box == Board.BOX_EMPTY && willTheShipFit(coordinates, activePlayer))
				return coordinates;
		}
	}

	private boolean willTheShipFit(Coordinates coordinates, int activePlayer) {
		int opponentPlayer = getIndexOpponentPlayer(activePlayer);
		int minimalNumberOfMasts = getMinimalNmberOfMastsFromNotHitShips(opponentPlayer);
		
		return CheckShotData.checkIsThereAPlaceWhenShot(coordinates, minimalNumberOfMasts);
	}

	private int getMinimalNmberOfMastsFromNotHitShips(int opponentPlayer) {
		return players[opponentPlayer].getCollectionShips().getMinimalNumberMastsOfNoHitShips();
	}

	private int getIndexOpponentPlayer(int activePlayer) {
		return (activePlayer == Player.SECOND_PLAYER) ? Player.FIRST_PLAYER : Player.SECOND_PLAYER;
	}

	private int getBoxFromPlayerBoard(Coordinates coordinates, int typeOfPlayer) {
		return players[typeOfPlayer].getBoard().getBox(coordinates);
	}

	private int getBoxFromOpponentBoard(Coordinates coordinates, int typeOfPlayer) {
		return players[typeOfPlayer].getOpponentBoard().getBox(coordinates);
	}
}
