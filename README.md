# Array and String Problems

This repository contains solutions to common array and string problems with both brute force and optimized approaches.

## Table of Contents

### Arrays
1. [Second Smallest and Second Largest element in an array](#second-smallest-and-second-largest-element-in-an-array)
2. [Reverse a given array](#reverse-a-given-array)
3. [Count frequency of each element in an array](#count-frequency-of-each-element-in-an-array)
4. [Rearrange array in increasing-decreasing order](#rearrange-array-in-increasing-decreasing-order)
5. [Remove duplicates from a sorted array](#remove-duplicates-from-a-sorted-array)
6. [Maximum sum subarray](#maximum-sum-subarray)

### Strings
1. [Check if a given string is palindrome or not](#check-if-a-given-string-is-palindrome-or-not)
2. [Remove all duplicates from the input string](#remove-all-duplicates-from-the-input-string)
3. [Reverse words in a string](#reverse-words-in-a-string)

---

## Arrays

### Second Smallest and Second Largest element in an array

**Question:** Find the second smallest and second largest elements in an array.

**Sample Test Cases:**
```
Input: arr = [12, 35, 1, 10, 34, 1]
Output: Second smallest: 10, Second largest: 34

Input: arr = [5, 5, 5, 5, 5]
Output: Second smallest: -1, Second largest: -1 (no second element)

Input: arr = [10, 5, 10]
Output: Second smallest: 10, Second largest: 5
```

#### 1) Brute Force Solution

```java
public class SecondMinMax {
    public static void findSecondMinMax(int[] arr) {
        int n = arr.length;
        
        if (n < 2) {
            System.out.println("Array has less than 2 elements");
            return;
        }
        
        // Sort the array
        Arrays.sort(arr);
        
        // Find second smallest (first element after removing duplicates)
        int secondSmallest = -1;
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[0]) {
                secondSmallest = arr[i];
                break;
            }
        }
        
        // Find second largest (last element before removing duplicates)
        int secondLargest = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] != arr[n - 1]) {
                secondLargest = arr[i];
                break;
            }
        }
        
        System.out.println("Second smallest: " + secondSmallest);
        System.out.println("Second largest: " + secondLargest);
    }
}
```

**Explanation:**
- Sort the array first
- Find second smallest by traversing from left and finding first element different from the smallest
- Find second largest by traversing from right and finding first element different from the largest
- Time Complexity: O(n log n) due to sorting
- Space Complexity: O(1)

#### 2) Optimized Solution

```java
public class SecondMinMaxOptimized {
    public static void findSecondMinMax(int[] arr) {
        int n = arr.length;
        
        if (n < 2) {
            System.out.println("Array has less than 2 elements");
            return;
        }
        
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        int largest = Integer.MIN_VALUE, secondLargest = Integer.MIN_VALUE;
        
        for (int i = 0; i < n; i++) {
            // Find second smallest
            if (arr[i] < first) {
                second = first;
                first = arr[i];
            } else if (arr[i] < second && arr[i] != first) {
                second = arr[i];
            }
            
            // Find second largest
            if (arr[i] > largest) {
                secondLargest = largest;
                largest = arr[i];
            } else if (arr[i] > secondLargest && arr[i] != largest) {
                secondLargest = arr[i];
            }
        }
        
        if (second == Integer.MAX_VALUE) {
            System.out.println("Second smallest: -1");
        } else {
            System.out.println("Second smallest: " + second);
        }
        
        if (secondLargest == Integer.MIN_VALUE) {
            System.out.println("Second largest: -1");
        } else {
            System.out.println("Second largest: " + secondLargest);
        }
    }
}
```

**Explanation:**
- Use single pass to find both second smallest and second largest
- Maintain four variables: first, second (for smallest), largest, secondLargest
- Update these variables in a single loop
- Time Complexity: O(n) - single pass
- Space Complexity: O(1)

---

### Reverse a given array

**Question:** Reverse the elements of an array.

**Sample Test Cases:**
```
Input: arr = [1, 2, 3, 4, 5]
Output: [5, 4, 3, 2, 1]

Input: arr = [10, 20, 30]
Output: [30, 20, 10]

Input: arr = [1]
Output: [1]
```

#### 1) Brute Force Solution

```java
public class ReverseArray {
    public static void reverseArray(int[] arr) {
        int n = arr.length;
        int[] reversed = new int[n];
        
        // Copy elements in reverse order
        for (int i = 0; i < n; i++) {
            reversed[i] = arr[n - 1 - i];
        }
        
        // Copy back to original array
        for (int i = 0; i < n; i++) {
            arr[i] = reversed[i];
        }
        
        System.out.print("Reversed array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
```

**Explanation:**
- Create a new array to store reversed elements
- Copy elements from original array to new array in reverse order
- Copy back to original array
- Time Complexity: O(n)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class ReverseArrayOptimized {
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        
        // Swap elements from both ends
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        
        System.out.print("Reversed array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
```

**Explanation:**
- Use two pointers: left and right
- Swap elements from both ends and move pointers inward
- Continue until left >= right
- Time Complexity: O(n)
- Space Complexity: O(1) - in-place reversal

---

### Count frequency of each element in an array

**Question:** Count the frequency of each element in an array.

**Sample Test Cases:**
```
Input: arr = [1, 2, 1, 3, 2, 1]
Output: 1 appears 3 times, 2 appears 2 times, 3 appears 1 time

Input: arr = [10, 20, 10, 20, 30]
Output: 10 appears 2 times, 20 appears 2 times, 30 appears 1 time

Input: arr = [1, 1, 1, 1]
Output: 1 appears 4 times
```

#### 1) Brute Force Solution

```java
public class CountFrequency {
    public static void countFrequency(int[] arr) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            
            int count = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] == arr[j]) {
                    visited[j] = true;
                    count++;
                }
            }
            System.out.println(arr[i] + " appears " + count + " times");
        }
    }
}
```

**Explanation:**
- Use a boolean array to mark visited elements
- For each unvisited element, count its frequency by comparing with all other elements
- Mark all occurrences as visited
- Time Complexity: O(n²)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class CountFrequencyOptimized {
    public static void countFrequency(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();
        
        // Count frequency
        for (int num : arr) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        
        // Print results
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            System.out.println(entry.getKey() + " appears " + entry.getValue() + " times");
        }
    }
}
```

**Explanation:**
- Use HashMap to store element-frequency pairs
- Traverse array once and increment count for each element
- Print the frequency map
- Time Complexity: O(n)
- Space Complexity: O(k) where k is number of unique elements

---

### Rearrange array in increasing-decreasing order

**Question:** Rearrange array so that first half is in increasing order and second half is in decreasing order.

**Sample Test Cases:**
```
Input: arr = [8, 7, 1, 6, 5, 9]
Output: [1, 5, 6, 9, 8, 7]

Input: arr = [4, 2, 8, 6, 15, 5, 9, 20]
Output: [2, 4, 5, 6, 20, 15, 9, 8]

Input: arr = [1, 2, 3, 4, 5]
Output: [1, 2, 3, 5, 4]
```

#### 1) Brute Force Solution

```java
public class IncreasingDecreasing {
    public static void rearrange(int[] arr) {
        int n = arr.length;
        
        // Sort the entire array
        Arrays.sort(arr);
        
        // Reverse the second half
        int mid = n / 2;
        for (int i = mid; i < (n + mid) / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[n - 1 - (i - mid)];
            arr[n - 1 - (i - mid)] = temp;
        }
        
        System.out.print("Rearranged array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
```

**Explanation:**
- Sort the entire array first
- Find the middle point
- Reverse the second half of the array
- Time Complexity: O(n log n) due to sorting
- Space Complexity: O(1)

#### 2) Optimized Solution

```java
public class IncreasingDecreasingOptimized {
    public static void rearrange(int[] arr) {
        int n = arr.length;
        
        // Sort the array
        Arrays.sort(arr);
        
        // Create result array
        int[] result = new int[n];
        int left = 0, right = n - 1;
        int index = 0;
        
        // Fill first half with smallest elements
        for (int i = 0; i < n / 2; i++) {
            result[index++] = arr[left++];
        }
        
        // Fill second half with largest elements in reverse
        for (int i = n / 2; i < n; i++) {
            result[index++] = arr[right--];
        }
        
        // Copy back to original array
        for (int i = 0; i < n; i++) {
            arr[i] = result[i];
        }
        
        System.out.print("Rearranged array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
```

**Explanation:**
- Sort the array first
- Use two pointers to fill result array
- First half: take smallest elements from left
- Second half: take largest elements from right
- Time Complexity: O(n log n) due to sorting
- Space Complexity: O(n)

---

### Remove duplicates from a sorted array

**Question:** Remove duplicates from a sorted array and return the new length.

**Sample Test Cases:**
```
Input: arr = [1, 1, 2, 2, 3, 4, 4, 5]
Output: Length = 5, Array = [1, 2, 3, 4, 5]

Input: arr = [1, 1, 1, 1]
Output: Length = 1, Array = [1]

Input: arr = [1, 2, 3, 4, 5]
Output: Length = 5, Array = [1, 2, 3, 4, 5]
```

#### 1) Brute Force Solution

```java
public class RemoveDuplicates {
    public static int removeDuplicates(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        // Create a new array to store unique elements
        int[] unique = new int[n];
        unique[0] = arr[0];
        int uniqueIndex = 1;
        
        // Copy unique elements
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[i - 1]) {
                unique[uniqueIndex++] = arr[i];
            }
        }
        
        // Copy back to original array
        for (int i = 0; i < uniqueIndex; i++) {
            arr[i] = unique[i];
        }
        
        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i < uniqueIndex; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
        return uniqueIndex;
    }
}
```

**Explanation:**
- Create a new array to store unique elements
- Compare current element with previous element
- If different, add to unique array
- Copy back to original array
- Time Complexity: O(n)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class RemoveDuplicatesOptimized {
    public static int removeDuplicates(int[] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        
        int uniqueIndex = 0;
        
        // Use two pointers technique
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[uniqueIndex]) {
                uniqueIndex++;
                arr[uniqueIndex] = arr[i];
            }
        }
        
        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i <= uniqueIndex; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
        return uniqueIndex + 1;
    }
}
```

**Explanation:**
- Use two pointers: one for unique elements, one for traversal
- When current element is different from last unique element, increment unique pointer and copy
- Time Complexity: O(n)
- Space Complexity: O(1) - in-place modification

---

### Maximum sum subarray

**Question:** Find the maximum sum of a contiguous subarray.

**Sample Test Cases:**
```
Input: arr = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
Output: Maximum sum = 6 (subarray [4, -1, 2, 1])

Input: arr = [1, 2, 3, 4, 5]
Output: Maximum sum = 15 (entire array)

Input: arr = [-1, -2, -3, -4]
Output: Maximum sum = -1 (subarray [-1])
```

#### 1) Brute Force Solution

```java
public class MaxSubarraySum {
    public static int maxSubarraySum(int[] arr) {
        int n = arr.length;
        int maxSum = Integer.MIN_VALUE;
        
        // Try all possible subarrays
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                int currentSum = 0;
                for (int k = start; k <= end; k++) {
                    currentSum += arr[k];
                }
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        System.out.println("Maximum sum: " + maxSum);
        return maxSum;
    }
}
```

**Explanation:**
- Try all possible start and end positions
- Calculate sum for each subarray
- Keep track of maximum sum found
- Time Complexity: O(n³)
- Space Complexity: O(1)

#### 2) Optimized Solution (Kadane's Algorithm)

```java
public class MaxSubarraySumOptimized {
    public static int maxSubarraySum(int[] arr) {
        int n = arr.length;
        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];
        
        for (int i = 1; i < n; i++) {
            // Either extend previous subarray or start new one
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        System.out.println("Maximum sum: " + maxSoFar);
        return maxSoFar;
    }
}
```

**Explanation:**
- Use Kadane's algorithm
- For each element, decide whether to extend previous subarray or start new one
- Keep track of maximum sum found so far
- Time Complexity: O(n)
- Space Complexity: O(1)

---

## Strings

### Check if a given string is palindrome or not

**Question:** Check if a string reads the same forwards and backwards.

**Sample Test Cases:**
```
Input: str = "racecar"
Output: true

Input: str = "hello"
Output: false

Input: str = "A man a plan a canal Panama"
Output: true (ignoring spaces and case)

Input: str = ""
Output: true
```

#### 1) Brute Force Solution

```java
public class PalindromeCheck {
    public static boolean isPalindrome(String str) {
        // Remove non-alphanumeric characters and convert to lowercase
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        // Create reversed string
        String reversed = "";
        for (int i = cleaned.length() - 1; i >= 0; i--) {
            reversed += cleaned.charAt(i);
        }
        
        return cleaned.equals(reversed);
    }
}
```

**Explanation:**
- Clean the string by removing non-alphanumeric characters
- Convert to lowercase
- Create reversed string by iterating backwards
- Compare original and reversed strings
- Time Complexity: O(n)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class PalindromeCheckOptimized {
    public static boolean isPalindrome(String str) {
        // Remove non-alphanumeric characters and convert to lowercase
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        int left = 0;
        int right = cleaned.length() - 1;
        
        // Compare characters from both ends
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
}
```

**Explanation:**
- Clean the string first
- Use two pointers from both ends
- Compare characters and move pointers inward
- If any mismatch found, return false
- Time Complexity: O(n)
- Space Complexity: O(n) for cleaned string

---

### Remove all duplicates from the input string

**Question:** Remove all duplicate characters from a string.

**Sample Test Cases:**
```
Input: str = "geeksforgeeks"
Output: "geksfor"

Input: str = "hello"
Output: "helo"

Input: str = "aabbcc"
Output: "abc"

Input: str = ""
Output: ""
```

#### 1) Brute Force Solution

```java
public class RemoveStringDuplicates {
    public static String removeDuplicates(String str) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            boolean isDuplicate = false;
            
            // Check if character already exists in result
            for (int j = 0; j < result.length(); j++) {
                if (result.charAt(j) == currentChar) {
                    isDuplicate = true;
                    break;
                }
            }
            
            if (!isDuplicate) {
                result.append(currentChar);
            }
        }
        
        return result.toString();
    }
}
```

**Explanation:**
- Use StringBuilder to build result string
- For each character, check if it already exists in result
- If not duplicate, append to result
- Time Complexity: O(n²)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class RemoveStringDuplicatesOptimized {
    public static String removeDuplicates(String str) {
        Set<Character> seen = new HashSet<>();
        StringBuilder result = new StringBuilder();
        
        for (char c : str.toCharArray()) {
            if (!seen.contains(c)) {
                seen.add(c);
                result.append(c);
            }
        }
        
        return result.toString();
    }
}
```

**Explanation:**
- Use HashSet to track seen characters
- For each character, check if already seen
- If not seen, add to set and append to result
- Time Complexity: O(n)
- Space Complexity: O(n)

---

### Reverse words in a string

**Question:** Reverse the order of words in a string while preserving word characters.

**Sample Test Cases:**
```
Input: str = "hello world"
Output: "world hello"

Input: str = "the sky is blue"
Output: "blue is sky the"

Input: str = "a good example"
Output: "example good a"

Input: str = "  hello world  "
Output: "world hello"
```

#### 1) Brute Force Solution

```java
public class ReverseWords {
    public static String reverseWords(String str) {
        // Split by whitespace and filter empty strings
        String[] words = str.trim().split("\\s+");
        
        // Reverse the array
        for (int i = 0; i < words.length / 2; i++) {
            String temp = words[i];
            words[i] = words[words.length - 1 - i];
            words[words.length - 1 - i] = temp;
        }
        
        // Join with space
        return String.join(" ", words);
    }
}
```

**Explanation:**
- Split string by whitespace using regex
- Reverse the array of words
- Join words back with space
- Time Complexity: O(n)
- Space Complexity: O(n)

#### 2) Optimized Solution

```java
public class ReverseWordsOptimized {
    public static String reverseWords(String str) {
        // Trim and split
        String[] words = str.trim().split("\\s+");
        
        // Use StringBuilder for efficient string building
        StringBuilder result = new StringBuilder();
        
        // Append words in reverse order
        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);
            if (i > 0) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }
}
```

**Explanation:**
- Split string and trim whitespace
- Use StringBuilder for efficient concatenation
- Append words in reverse order
- Add space between words except for last word
- Time Complexity: O(n)
- Space Complexity: O(n)

---

## Summary

This collection covers fundamental array and string problems with both brute force and optimized solutions. The brute force solutions are designed to be easy to understand and implement, while the optimized solutions focus on efficiency and better time/space complexity.

### Key Takeaways:
- **Arrays**: Two-pointer technique, sorting, and dynamic programming are common optimization strategies
- **Strings**: Character frequency counting, two-pointer technique, and string manipulation are key concepts
- **Time Complexity**: Brute force solutions often have O(n²) or O(n³) complexity, while optimized solutions achieve O(n) or O(n log n)
- **Space Complexity**: Optimized solutions often reduce space complexity from O(n) to O(1) for in-place operations

Each problem demonstrates the importance of understanding the problem constraints and choosing the appropriate algorithm based on the requirements. 