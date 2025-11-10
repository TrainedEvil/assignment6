package com.example.playwrightTraditional;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright
public class BookstoreTest {

  @Test
  void fullBookstoreFlow(Page page) {
    // Test 1: Bookstore
    page.navigate("https://depaul.bncollege.com/");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
    page.locator(".facet__list.js-facet-list.js-facet-top-values > li:nth-child(3) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
    page.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
    page.locator("#facet-price > .facet__values > .facet__list > li:nth-child(2) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
    assertThat(page.getByLabel("main").getByRole(AriaRole.HEADING)).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
    assertThat(page.getByLabel("main")).containsText("sku 668972707");
    assertThat(page.getByLabel("main")).containsText("$164.98");
    assertThat(page.getByLabel("main")).containsText("Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms.");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
    assertThat(page.locator("#headerDesktopView")).containsText("1 items");
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();

    // Test 2: Shopping Cart
    assertThat(page.getByLabel("main")).containsText("Your Shopping Cart");
    assertThat(page.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
    assertThat(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Quantity, edit and press"))).hasValue("1");
    assertThat(page.getByLabel("main")).containsText("$164.98");
    page.getByText("FAST In-Store PickupDePaul").click();
    assertThat(page.getByLabel("main")).containsText("Subtotal $164.98");
    assertThat(page.getByLabel("main")).containsText("Handling");
    assertThat(page.getByLabel("main")).containsText("Taxes TBD");
    assertThat(page.getByLabel("main")).containsText("$167.98");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();
    assertThat(page.locator("#js-voucher-result")).containsText("The coupon code entered is not valid.");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();

    // Test 3: Create Account Page
    assertThat(page.getByLabel("main")).containsText("Create Account");
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

    // Test 4: Contact Information Page
    assertThat(page.getByLabel("main")).containsText("Contact Information");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Dave");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Jones");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("bmc1522@gmail.com");
    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("8726007982");
    assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
    assertThat(page.getByLabel("main")).containsText("Tax TBD");
    assertThat(page.getByLabel("main")).containsText("$167.98");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

    // Test 5: Pickup Information Page
    assertThat(page.getByLabel("main")).containsText("Full Name Dave Jones Email Address bmc1522@gmail.com Phone Number +18726007982");
    assertThat(page.locator("#bnedPickupPersonForm")).containsText("Pickup Location DePaul University Loop Campus & SAIC");
    page.getByText("I'll pick them up").click();
    assertThat(page.locator("#bnedPickupPersonForm")).containsText("I'll pick them up");
    assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

    // Test 6: Payment Information Page
    assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
    assertThat(page.getByLabel("main")).containsText("Tax");
    assertThat(page.getByLabel("main")).containsText("PICKUP DePaul University Loop Campus & SAIC JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();

    // Test 7: Shopping Cart (Delete)
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
    assertThat(page.getByLabel("main").getByRole(AriaRole.HEADING)).containsText("Your cart is empty");
  }
}
