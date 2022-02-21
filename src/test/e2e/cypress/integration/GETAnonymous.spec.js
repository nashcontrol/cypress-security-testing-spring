
describe('User api', () => {
    context('GET /api/user/anonymous', () => {
        it('should return a hello anonymous response', () => {
            cy.request({
                method: 'GET',
                url: 'https://serverest.dev/api/user/anonymous'
            })
                .should((response) => {
                    expect(response.status).to.eq(200)
                    expect(response.body).to.eq("Hello Anonymous user")
                });
        });
    });
});