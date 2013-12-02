package nallar.tickthreading.global;

import nallar.tickthreading.util.BooleanThreadLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@SuppressWarnings("UnusedDeclaration")
public class ThreadLocals {
	public static final ThreadLocal redPowerBlockUpdateSet = new HashSetThreadLocal();
	public static final ThreadLocal redPowerPowerSearch = new LinkedListThreadLocal();
	public static final ThreadLocal redPowerPowerSearchTest = new HashSetThreadLocal();
	public static final BooleanThreadLocal redPowerIsSearching = new BooleanThreadLocal();
	public static final ThreadLocal eligibleChunksForSpawning = new HashMapThreadLocal();
	public static final ThreadLocal factorizationFindLightAirParentToVisit = new HashSetThreadLocal();
	public static final ThreadLocal mapChunkTempByteArray = new MapChunkTempByteArrayThreadLocal();

	private static class ArrayListThreadLocal extends ThreadLocal {
		ArrayListThreadLocal() {
		}

		@Override
		protected Object initialValue() {
			return new ArrayList();
		}
	}

	private static class HashMapThreadLocal extends ThreadLocal {
		HashMapThreadLocal() {
		}

		@Override
		protected Object initialValue() {
			return new HashMap();
		}
	}

	private static class HashSetThreadLocal extends ThreadLocal {
		HashSetThreadLocal() {
		}

		@Override
		protected Object initialValue() {
			return new HashSet();
		}
	}

	private static class LinkedListThreadLocal extends ThreadLocal {
		LinkedListThreadLocal() {
		}

		@Override
		protected Object initialValue() {
			return new LinkedList();
		}
	}

	public static class MapChunkTempByteArrayThreadLocal extends ThreadLocal<byte[]> {
		@Override
		public byte[] initialValue() {
			return new byte[196864];
		}
	}
}
