package com.niemiec.battleship.game.objects;

import java.io.Serializable;

//W tej klasie zamieniamy dane wprowadzane na dane tablicowe
//W grze pole 1,1 to w tablicy 0,0
//Board - tablica
@SuppressWarnings("serial")
public class Board implements Serializable, Cloneable {
	public static final int BOX_EMPTY = 0;
	public static final int BOX_NOT_HIT = 1;
	public static final int BOX_SHIP = 2;
	public static final int BOX_HIT = 3;
	public static final int BOX_ENTER = 4;
	public static final int BOX_SUNK = 5;

	int[][] boxes;

	public Board() {
		this.boxes = new int[10][10];
		resetArray(boxes);
	}

	public int getBox(Coordinates box) {
		return this.boxes[box.getY() - 1][box.getX() - 1];
	}

	// ustawia warto�� danej kom�rki
	public void setBox(Coordinates box, int stateOfBox) {
		this.boxes[box.getY() - 1][box.getX() - 1] = stateOfBox;
	}

	// Ustawia w tablicy same zera
	private void resetArray(int[][] a) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				a[i][j] = BOX_EMPTY;
	}

	public void checkBoxEnterToBoxShip() {
		for (int i = 0; i < boxes.length; i++)
			for (int j = 0; j < boxes[i].length; j++)
				if (boxes[i][j] == BOX_ENTER)
					boxes[i][j] = BOX_SHIP;
	}

	// i jako X
	// j jako Y
	public void viewBoard() {
		char a = 'A';
		System.out.print("    ");
		for (int i = 0; i < 10; i++)
			System.out.print(a++ + "  ");
		System.out.println("");
		for (int i = 0; i < 10; i++) {
			if (i < 9)
				System.out.print((i + 1) + "  ");
			else
				System.out.print((i + 1) + " ");

			for (int j = 0; j < 10; j++) {
				System.out.print(" " + boxes[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	private void setBoxes(int[][] boxes) {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				this.boxes[i][j] = boxes[i][j];
			}
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Board b = new Board();
		b.setBoxes(boxes.clone());
		return b;
	}
}
