package dev.kyma.samples.trigger.model;

import java.util.Objects;

public class OrderDetails {
    private String orderId;
    private boolean cancellable;
    private TotalPriceWithTax totalPriceWithTax;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", cancellable=" + cancellable +
                ", totalPriceWithTax=" + totalPriceWithTax +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetails that = (OrderDetails) o;

        if (cancellable != that.cancellable) return false;
        if (!orderId.equals(that.orderId)) return false;
        return totalPriceWithTax.equals(that.totalPriceWithTax);
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + (cancellable ? 1 : 0);
        result = 31 * result + totalPriceWithTax.hashCode();
        return result;
    }

    public TotalPriceWithTax getTotalPriceWithTax() {
        return totalPriceWithTax;
    }

    public void setTotalPriceWithTax(TotalPriceWithTax totalPriceWithTax) {
        this.totalPriceWithTax = totalPriceWithTax;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static class TotalPriceWithTax {
        private Long value;

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TotalPriceWithTax that = (TotalPriceWithTax) o;

            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "TotalPriceWithTax{" +
                    "value=" + value +
                    '}';
        }
    }
}
