package com.jgw.common;

import android.text.TextUtils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LeetCodeTest {


    @Test
    public void testTwoSum() {
        String str = "abcabcbb";
        int i = lengthOfLongestSubstring(str);
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = target - nums[i];
            if (map.containsKey(num)) {
                return new int[]{map.get(num), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean jinYi;
        int sum = l1.val + l2.val;
        jinYi = sum > 9;
        sum = sum % 10;
        ListNode origin = new ListNode(sum);
        ListNode listNode = origin;
        l1 = l1.next;
        l2 = l2.next;
        while (l1 != null || l2 != null || jinYi) {
            int var1 = l1 == null ? 0 : l1.val;
            int var2 = l2 == null ? 0 : l2.val;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
            sum = var1 + var2;
            sum = jinYi ? sum + 1 : sum;
            jinYi = sum > 9;
            sum = sum % 10;
            listNode.next = new ListNode(sum);
            listNode = listNode.next;
        }
        return origin;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public int lengthOfLongestSubstring(String s) {
        if (s==null||s.length()==0){
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        int max=1;
        int start=0;
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            if (map.containsKey(key)){
                start=map.get(key)+1;
            }
            map.put(key,i);
            max=Math.max(max,i-start+1);
        }
        return max;
    }
}