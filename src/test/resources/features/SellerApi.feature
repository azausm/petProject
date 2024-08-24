Feature: test seller api

  @getSellerVerifyEmailNotEmpty @regression
  Scenario: get a single seller and verify email is not empty
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then verify seller email is not empty

    @getAllSellers @regression
    Scenario: get all sellers and verify sellers id is not 0
      Given user hits get all sellers api with "/api/myaccount/sellers"
      Then verify sellers id is not 0

     @uptadeSeller @regression
      Scenario: get single seller, update the same seller, verify seller was updated
       Given user hits get single seller api with "/api/myaccount/sellers/"
       Then verify seller email is not empty
       Then user hits put api with "/api/myaccount/sellers/"
       Then verify user email was updated
       And verify user first name was updated


       @archiveSeller @regression
       Scenario: get all sellers, archive all sellers and verify sellers are archived
         Given user hits get single seller api with "/api/myaccount/sellers/"
         Then user hits the api with "/api/myaccount/sellers/archive/unarchive" to archive the seller
         Then user hits get all archived sellers api with "/api/myaccount/sellers"
         Then user verify seller is archived



         @CreateDelete @regression
         Scenario: Create a seller, delete a seller, verify seller was deleted
           Given user hits post api with "/api/myaccount/sellers"
           Then verify seller id was generetad
           Then verify seller name is not empty
           And verify seller email is not empty
           Then delete the seller with "/api/myaccount/sellers/"
           Then user hits get all sellers api with "/api/myaccount/sellers"
           Then verify deleted seller is not in the list

