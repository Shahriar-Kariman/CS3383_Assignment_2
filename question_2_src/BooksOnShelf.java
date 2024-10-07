import java.util.Arrays;
import java.util.LinkedList;;

public class BooksOnShelf {

  // Merge Sort helper to sort the heights
  public static int[] mergeSort(int[] books) {
    if (books.length <= 1) {
      return books;
    }

    int mid = books.length / 2;
    int[] left = Arrays.copyOfRange(books, 0, mid);
    int[] right = Arrays.copyOfRange(books, mid, books.length);

    return merge(mergeSort(left), mergeSort(right));
  }

  private static int[] merge(int[] left, int[] right) {
    int[] result = new int[left.length + right.length];
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
      if (left[i] >= right[j]) {
        result[k++] = left[i++];
      } else {
        result[k++] = right[j++];
      }
    }

    while (i < left.length) {
      result[k++] = left[i++];
    }

    while (j < right.length) {
      result[k++] = right[j++];
    }

    return result;
  }

  public static int booksOnShelf(int[] books) {
    int[] sortedBooks = mergeSort(books);
    System.out.println(Arrays.toString(sortedBooks));
    LinkedList<int[]> shelfList = new LinkedList<int[]>();
    int[] shelf = new int[10];

    int j = 0;
    for(int i=0; i<sortedBooks.length;i++){
      shelf[j] = sortedBooks[i];
      j++;
      if(j==10){
        shelfList.add(shelf);
        j=0;
        shelf = new int[10];
      }
    }

    while(j<10){
      shelf[j] = -1;
      j++;
    }

    shelfList.add(shelf);

    int shelfHeight = 0;
    for(int i=0; i<shelfList.size(); i++){
      shelf = shelfList.get(i);
      int max = 0;
      for(int k = 0; k < 10; k++){
        if(shelf[k]>max) max = shelf[k];
      }
      shelfHeight += max;
      System.out.println(max);
    }

    return shelfHeight;
  }

  public static void main(String[] args) {
    if(args.length > 0){
      int[] books = new int[Integer.parseInt(args[0])];
      for(int i=0; i<books.length; i++){
        books[i] = Integer.parseInt(args[i+1]);
      }
      int totalHeight = booksOnShelf(books);
      System.out.println("Total Shelf Height: " + totalHeight);
    }
  }
}
