package be.thomasmore.party.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Snack {
    @Id
    private int id;
    private String name;
    private boolean vegan;
    private boolean sideDishPossible;
    private double price;
    private Double priceSideDish;
    //vraag 2: neen dit gaat niet, met de reden dat een double niet null kan zijn maar een Double wel null kan zijn
    @ManyToMany
    private Collection<Venue> venues;

    public Snack() {
    }

    public Collection<Venue> getVenues() {
        return venues;
    }

    public void setVenues(Collection<Venue> venues) {
        this.venues = venues;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isSideDishPossible() {
        return sideDishPossible;
    }

    public void setSideDishPossible(boolean sideDishPossible) {
        this.sideDishPossible = sideDishPossible;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPriceSideDish() {
        return priceSideDish;
    }

    public void setPriceSideDish(Double priceSideDish) {
        this.priceSideDish = priceSideDish;
    }
}
