package com.mercadolibre.workshop.v5;

class BlockingService {
    private BlockingService() {
    }

    static BlockingService getInstance() {
        return Holder.INSTANCE;
    }

    String calculate() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hash";
    }

    private static class Holder {
        static final BlockingService INSTANCE = new BlockingService();
    }
}
