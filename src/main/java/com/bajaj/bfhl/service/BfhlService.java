package com.bajaj.bfhl.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BfhlService {

    public List<Integer> fibonacci(int n) {
        List<Integer> res = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            res.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return res;
    }

    public List<Integer> prime(List<Integer> nums) {
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            if (n > 1 && isPrime(n)) res.add(n);
        }
        return res;
    }

    private boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }

    public int hcf(List<Integer> nums) {
        int ans = nums.get(0);
        for (int n : nums)
            ans = gcd(ans, n);
        return ans;
    }

    public int lcm(List<Integer> nums) {
        int ans = nums.get(0);
        for (int n : nums)
            ans = (ans * n) / gcd(ans, n);
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
