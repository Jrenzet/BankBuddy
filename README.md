# Financial Planner for Businesses

## A straightforward way to analyse your businesses debt

**Description:**

I will be designing a financial business planning app with emphasis on debt planning. This app will allow the user to 
input specific line items from their past financial statements along with their current debt load, and calculate Debt 
Service Coverage (a key metric used by banks to evaluate their businesses). The app will also have the ability to use 
this information to generate projections for the user, with the user being able to add and adjust the following in 
their projections: new debt, increased income from capital investment/expansion, projected interest rates, projected 
income. The application will have the capability to print a summary of debt and projections, allowing for the user to 
summarize data for decision-making and for presenting to prospective lenders. Finally, the application will evaluate the
user’s likelihood of attaining new debt, by using the projection to return a rating of either green, yellow, or red, 
meaning high likelihood of being approved, lesser likelihood of being approved, and low likelihood of being approved.

The user of this application will be any business owner or controller of a small to medium size business who is 
considering beginning a new project and would like to evaluate the debt/cash flow implications of this. The projected
user has business savvy and is financially literate, however they would like assistance in summarizing data in a way
that is useful for their financial institution and may even allow them to increase their chances of getting an approval.
I am interested in this project as I used to work in the commercial banking sector and believe an application like this
could be very useful for some business owners. 

**Summary:**
- Users can learn about their businesses financial health
- Users can run their own projections

## User Stories

- As a user, I want to be able to add new loans to a financial projection.
- As a user, I want to be able to view a list of my existing debt, with key information for each loan
- As a user, I want to be able to store information from past financial statements for use in projections.
- As a user, I want the system to use stored information to automatically generate a projection with my expected DSC,
while giving me the ability to edit aspects of this projection (i.e. loans and financial statements).
- As a user, I want to be able to generate a rating of my likelihood of being approved for new debt.
- As a user, I want to have the option to save the state of my projection before quitting the program.
- As a user, I want to have the option to load my previously saved projection when starting up the program.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple loans to a financial projection"
by clicking the "Loans" button, filling in all boxes (integer for remaining term, double for interest rate and balance, 
and string for "what is this loan for"), the clicking submit. A loan with the entered information has now been added to
the financial projection.
- You can generate the second required action related to the user story "adding multiple loans to a financial 
projection" by clicking the "Loans" button, then clicking the "Edit Loans" button, filling in the loan number field with
an integer (equal to the number of a loan that has been added), selecting an option form the dropdown box, inputting a
value to replace the old one, then clicking submit. The specified loan has now been edited with your inputted value.
- You can view all loans that have been added to the projection  by clicking the view loans button, which displays all 
loans and will reflect any user changes.
- You can locate my visual component by starting up the application, there is an image displayed on the home screen.
- You can save the state of my application by clicking the file menu item on the home screen, then clicking save.
- You can reload the state of my application by clicking the file menu item on the home screen, then clicking load.

## Sources
- JsonSerializationDemo project from CPSC 210 repository
- ListDemo Oracle, Java Documentation

 
