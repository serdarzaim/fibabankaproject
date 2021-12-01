# fibabankaproject
<br /><br />
Postman requests are attached.<br /><br />

**/api/users/register**<br />
This API call creates an user.<br />
User must provide informations below in the body of the request.<br />
TCKN, First Name, Last Name, Email, Password<br />
User recieves UserId and authentication token.<br />
<br />
**/api/users/login**<br />
Users login with this  API call.<br />
User must provide informations below in the body of the request.<br />
TCKN, Password<br />
User recieves an authentication token.<br />
<br />
**/api/users/updateuser**<br />
This API call updates already existing user.<br />
User must provide informations below in the body of the request.<br />
Also user must provide valid authentication token in the headers.<br />
TCKN can not be changed. <br />
TCKN, First Name, Last Name, Email, Password<br />
<br />
**/api/accounts/all**<br />
This API call gets all user informations.<br />
User must provide informations below in the body of the request.<br />
Also user must provide valid authentication token in the headers.<br />
userID<br />
This call returns all information about the users accounts.<br />
<br />
**/api/accounts/create**<br />
This API call creates an account for the user.<br />
Users can create multiple accounts.<br />
User must provide informations below in the body of the request.<br />
Also user must provide valid authentication token in the headers.<br />
Balance can not be null.<br />
User Id, Title, Description , Balance, Currency<br />
<br />
**/api/accounts/delete/{accountId}**<br />
This api call deletes an account for the user.<br />
User must provide informations below in the body of the request.<br />
Also user must provide valid authentication token in the headers.<br />
User must provide accountid in the URL.<br />
User Id<br />
<br />
**/api/transactions/moneytransfer**<br />
This api call transfer amount between two accounts.<br />
User must provide informations below in the body of the request.<br />
Also user must provide valid authentication token in the headers.<br />
Sender Account ID, Reciepent Account ID, Amount, Note<br />
