package com.labsky.timotej.model.products;

import com.labsky.timotej.exceptions.SimCardCountRestrictionException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.constraints.Constrain;
import com.labsky.timotej.model.products.promotions.SalePromotion;
import com.labsky.timotej.util.ProductCountPair;

import java.util.List;
import java.util.Optional;

/**
 * @author timotej
 */
public class SimCard extends GenericProduct implements Constrain {
    public static final int MAX_NUMBER_IN_BASKET = 10;

    public SimCard() {
    }

    public SimCard(String name, Double price, String currency, List<SalePromotion> salePromotions) {
        super(name, price, currency, salePromotions);
    }

    @Override
    public boolean isValid(Basket basket) throws SimCardCountRestrictionException {
        Optional<ProductCountPair> thisProductCountPair = basket.getProducts().stream()
                .filter(pcp -> pcp.product() == this)
                .findFirst();
        if (thisProductCountPair.isPresent() && thisProductCountPair.get().count() > MAX_NUMBER_IN_BASKET) {
            throw new SimCardCountRestrictionException("number of sim is higher than allowed %d > %d".formatted(thisProductCountPair.get().count(), MAX_NUMBER_IN_BASKET));
        }

        return true;
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public SimCard build() {
            return new SimCard(this.name, this.price, this.currency, this.salePromotions);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
